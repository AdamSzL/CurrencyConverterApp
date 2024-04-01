package com.example.currencyconverterapp.watchlist.presentation.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.watchlist.data.model.ExchangeRateRelation
import com.example.currencyconverterapp.watchlist.data.model.WatchlistData
import com.example.currencyconverterapp.watchlist.data.model.WatchlistItem
import com.example.currencyconverterapp.watchlist.data.repository.LatestExchangeRatesRepository
import com.example.currencyconverterapp.watchlist.data.repository.WatchlistDataRepository
import com.example.currencyconverterapp.watchlist.data.repository.WatchlistWorkManagerRepository
import com.example.currencyconverterapp.watchlist.presentation.item.latest_exchange_rate.LatestExchangeRateUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class WatchlistItemViewModel @Inject constructor(
    private val watchlistDataRepository: WatchlistDataRepository,
    private val latestExchangeRatesRepository: LatestExchangeRatesRepository,
    private val watchlistWorkManagerRepository: WatchlistWorkManagerRepository,
    private val state: SavedStateHandle,
): ViewModel() {

    private val _watchlistItemUiState = MutableStateFlow(WatchlistItemUiState())
    val watchlistItemUiState: StateFlow<WatchlistItemUiState> = _watchlistItemUiState

    var targetValue by mutableStateOf("1.00")
        private set

    init {
        val watchlistItemId = state.get<String>("watchlist_item_id")
        viewModelScope.launch {
            watchlistDataRepository.watchlistData.first { watchlistData ->
                if (watchlistItemId != null) {
                    getItemAndUpdateUiState(watchlistData, watchlistItemId)
                } else {
                    _watchlistItemUiState.update {
                        it.copy(
                            isNotificationsPermissionPermanentlyRejected = watchlistData.isNotificationsPermissionPermanentlyRejected
                        )
                    }
                }
                fetchLatestExchangeRate()
                true
            }
        }
    }

    private fun getItemAndUpdateUiState(watchlistData: WatchlistData, itemId: String) {
        val watchlistItem = watchlistData.watchlistItems.first { it.id == itemId }
        _watchlistItemUiState.update {
            it.copy(
                itemId = itemId,
                baseCurrency = watchlistItem.baseCurrency,
                targetCurrency = watchlistItem.targetCurrency,
                exchangeRateRelation = watchlistItem.exchangeRateRelation,
                latestExchangeRateUiState = LatestExchangeRateUiState.Loading,
                isNotificationsPermissionPermanentlyRejected = watchlistData.isNotificationsPermissionPermanentlyRejected,
            )
        }
        targetValue = watchlistItem.targetValue
    }

    fun resetBackToAddition() {
        _watchlistItemUiState.update {
            it.copy(
                itemId = null
            )
        }
    }

    fun updateSelectedItem(itemId: String) {
        viewModelScope.launch {
            watchlistDataRepository.watchlistData.first { watchlistData ->
                getItemAndUpdateUiState(watchlistData, itemId)
                fetchLatestExchangeRate()
                true
            }
        }
    }

    fun selectBaseCurrency(baseCurrency: Currency) {
        _watchlistItemUiState.update {
            it.copy(
                baseCurrency = baseCurrency,
            )
        }
        restoreToLoadingStateAndFetchExchangeRate()
    }

    fun selectTargetCurrency(targetCurrency: Currency) {
        _watchlistItemUiState.update {
            it.copy(
                targetCurrency = targetCurrency,
            )
        }
        restoreToLoadingStateAndFetchExchangeRate()
    }

    fun selectExchangeRateRelation(exchangeRateRelation: ExchangeRateRelation) {
        _watchlistItemUiState.update {
            it.copy(
                exchangeRateRelation = exchangeRateRelation
            )
        }
    }

    fun updateTargetValue(newTargetValue: String) {
        targetValue = newTargetValue
    }

    fun swapBaseAndTargetCurrencies() {
        _watchlistItemUiState.update {
            it.copy(
                baseCurrency = it.targetCurrency,
                targetCurrency = it.baseCurrency,
            )
        }
        restoreToLoadingStateAndFetchExchangeRate()
    }

    fun restoreToLoadingStateAndFetchExchangeRate() {
        _watchlistItemUiState.update {
            it.copy(
                latestExchangeRateUiState = LatestExchangeRateUiState.Loading
            )
        }
        fetchLatestExchangeRate()
    }

    private fun fetchLatestExchangeRate() {
        viewModelScope.launch {
            val latestExchangeRateUiState = try {
                val exchangeRate = latestExchangeRatesRepository.getLatestExchangeRates(
                    baseCurrency = watchlistItemUiState.value.baseCurrency.code,
                    currencies = watchlistItemUiState.value.targetCurrency.code,
                ).data.values.toList().first().value
                val lastUpdatedAt = Clock.System.now().toString()
                LatestExchangeRateUiState.Success(
                    exchangeRate!!,
                    lastUpdatedAt
                )
            } catch (e: Exception) {
                delay(200)
                LatestExchangeRateUiState.Error
            }
            _watchlistItemUiState.update {
                it.copy(
                    latestExchangeRateUiState = latestExchangeRateUiState
                )
            }
        }
    }

    fun addWatchlistItem(watchlistItem: WatchlistItem) {
        viewModelScope.launch {
            watchlistDataRepository.addWatchlistItem(watchlistItem)
        }
        watchlistWorkManagerRepository.startWatchlistWork()
    }

    fun editWatchlistItem(watchlistItem: WatchlistItem) {
        viewModelScope.launch {
            watchlistDataRepository.updateWatchlistItem(watchlistItem)
        }
    }

    fun updateNotificationsPermissionRejectionState(state: Boolean) {
        _watchlistItemUiState.update {
            it.copy(
                isNotificationsPermissionPermanentlyRejected = state
            )
        }
        viewModelScope.launch {
            watchlistDataRepository.updateNotificationsPermissionRejectionState(state)
        }
    }
}