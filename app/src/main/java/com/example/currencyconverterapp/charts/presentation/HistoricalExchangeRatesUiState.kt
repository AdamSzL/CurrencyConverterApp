package com.example.currencyconverterapp.charts.presentation

sealed interface HistoricalExchangeRatesUiState {
    data object Success: HistoricalExchangeRatesUiState
    data object Error: HistoricalExchangeRatesUiState
    data object ErrorButCached: HistoricalExchangeRatesUiState
    data object Loading: HistoricalExchangeRatesUiState
}