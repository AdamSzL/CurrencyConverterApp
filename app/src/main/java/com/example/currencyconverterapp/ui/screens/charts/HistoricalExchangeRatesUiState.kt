package com.example.currencyconverterapp.ui.screens.charts

sealed interface HistoricalExchangeRatesUiState {
    data object Success: HistoricalExchangeRatesUiState
    data object Error: HistoricalExchangeRatesUiState
    data object Loading: HistoricalExchangeRatesUiState
}