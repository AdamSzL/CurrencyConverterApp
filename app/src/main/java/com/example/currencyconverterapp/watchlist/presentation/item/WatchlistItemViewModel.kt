package com.example.currencyconverterapp.watchlist.presentation.item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.watchlist.data.model.ExchangeRateRelation
import com.example.currencyconverterapp.watchlist.data.model.WatchlistItem
import com.example.currencyconverterapp.watchlist.data.repository.LatestExchangeRatesRepository
import com.example.currencyconverterapp.watchlist.data.repository.WatchlistDataRepository
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
    private val state: SavedStateHandle
): ViewModel() {

    private val _watchlistItemUiState = MutableStateFlow(WatchlistItemUiState())
    val watchlistItemUiState: StateFlow<WatchlistItemUiState> = _watchlistItemUiState

    init {
        val watchlistItemId = state.get<String>("watchlist_item_id")
        viewModelScope.launch {
            watchlistDataRepository.watchlistData.first { watchlistData ->
                if (watchlistItemId != null) {
                    val watchlistItem = watchlistData.watchlistItems.first { it.id == watchlistItemId }
                    _watchlistItemUiState.update {
                        it.copy(
                            itemId = watchlistItemId,
                            baseCurrency = watchlistItem.baseCurrency,
                            targetCurrency = watchlistItem.targetCurrency,
                            exchangeRateRelation = watchlistItem.exchangeRateRelation,
                            targetValue = watchlistItem.targetValue,
                            latestExchangeRateUiState = LatestExchangeRateUiState.Loading,
                            isNotificationsPermissionPermanentlyRejected = watchlistData.isNotificationsPermissionPermanentlyRejected,
                        )
                    }
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

    fun changeTargetValue(targetValue: Double) {
        _watchlistItemUiState.update {
            it.copy(
                targetValue = targetValue,
            )
        }
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
            } catch (e: IOException) {
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