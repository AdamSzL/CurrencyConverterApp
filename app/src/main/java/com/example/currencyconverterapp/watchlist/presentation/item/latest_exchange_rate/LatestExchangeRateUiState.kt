package com.example.currencyconverterapp.watchlist.presentation.item.latest_exchange_rate

sealed interface LatestExchangeRateUiState {

    data class Success(val exchangeRate: Double, val lastUpdatedAt: String):
        LatestExchangeRateUiState

    data object Error: LatestExchangeRateUiState

    data object Loading: LatestExchangeRateUiState
}