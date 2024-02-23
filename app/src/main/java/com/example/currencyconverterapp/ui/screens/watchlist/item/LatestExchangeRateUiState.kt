package com.example.currencyconverterapp.ui.screens.watchlist.item

sealed interface LatestExchangeRateUiState {
    data class Success(val exchangeRate: Double, val lastUpdatedAt: String):
        LatestExchangeRateUiState
    data object Error: LatestExchangeRateUiState
    data object Loading: LatestExchangeRateUiState
}