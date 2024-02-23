package com.example.currencyconverterapp.ui.screens.watchlist.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.data.CurrencyConverterRepository
import com.example.currencyconverterapp.data.WatchlistDataRepository
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.ExchangeRateRelation
import com.example.currencyconverterapp.model.WatchlistItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class WatchlistAddItemViewModel @Inject constructor(
    private val watchlistDataRepository: WatchlistDataRepository,
    private val currencyConverterRepository: CurrencyConverterRepository,
): ViewModel() {

    private val _watchlistAddItemUiState = MutableStateFlow(WatchlistAddItemUiState())
    val watchlistAddItemUiState: StateFlow<WatchlistAddItemUiState> = _watchlistAddItemUiState

    init {
        fetchLatestExchangeRate()
    }

    fun selectBaseCurrency(baseCurrency: Currency) {
        _watchlistAddItemUiState.update {
            it.copy(
                baseCurrency = baseCurrency,
            )
        }
        restoreToLoadingStateAndFetchExchangeRate()
    }

    fun selectTargetCurrency(targetCurrency: Currency) {
        _watchlistAddItemUiState.update {
            it.copy(
                targetCurrency = targetCurrency,
            )
        }
        restoreToLoadingStateAndFetchExchangeRate()
    }

    fun selectExchangeRateRelation(exchangeRateRelation: ExchangeRateRelation) {
        _watchlistAddItemUiState.update {
            it.copy(
                exchangeRateRelation = exchangeRateRelation
            )
        }
    }

    fun changeTargetValue(targetValue: Double) {
        _watchlistAddItemUiState.update {
            it.copy(
                targetValue = targetValue,
            )
        }
    }

    fun swapBaseAndTargetCurrencies() {
        _watchlistAddItemUiState.update {
            it.copy(
                baseCurrency = it.targetCurrency,
                targetCurrency = it.baseCurrency,
            )
        }
        restoreToLoadingStateAndFetchExchangeRate()
    }

    fun restoreToLoadingStateAndFetchExchangeRate() {
        _watchlistAddItemUiState.update {
            it.copy(
                latestExchangeRateUiState = LatestExchangeRateUiState.Loading
            )
        }
        fetchLatestExchangeRate()
    }

    private fun fetchLatestExchangeRate() {
        viewModelScope.launch {
            val latestExchangeRateUiState = try {
                val exchangeRate = currencyConverterRepository.getLatestExchangeRates(
                    baseCurrency = watchlistAddItemUiState.value.baseCurrency.code,
                    currencies = watchlistAddItemUiState.value.targetCurrency.code,
                ).data.values.toList().first().value
                val lastUpdatedAt = Clock.System.now().toString()
                LatestExchangeRateUiState.Success(
                    exchangeRate!!,
                    lastUpdatedAt
                )
            } catch (e: IOException) {
                LatestExchangeRateUiState.Error
            }
            _watchlistAddItemUiState.update {
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
}