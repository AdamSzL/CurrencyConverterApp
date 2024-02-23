package com.example.currencyconverterapp.ui.screens.watchlist.add

import com.example.currencyconverterapp.data.defaultBaseCurrency
import com.example.currencyconverterapp.data.defaultTargetCurrency
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.ExchangeRateRelation

data class WatchlistAddItemUiState(
    val baseCurrency: Currency = defaultBaseCurrency,
    val targetCurrency: Currency = defaultTargetCurrency,
    val exchangeRateRelation: ExchangeRateRelation = ExchangeRateRelation.LESS_THAN,
    val targetValue: Double = 1.15,
    val latestExchangeRateUiState: LatestExchangeRateUiState = LatestExchangeRateUiState.Loading
)

sealed interface LatestExchangeRateUiState {
    data class Success(val exchangeRate: Double, val lastUpdatedAt: String): LatestExchangeRateUiState
    data object Error: LatestExchangeRateUiState
    data object Loading: LatestExchangeRateUiState
}
