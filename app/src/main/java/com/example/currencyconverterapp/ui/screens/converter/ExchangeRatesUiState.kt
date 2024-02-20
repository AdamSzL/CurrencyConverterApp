package com.example.currencyconverterapp.ui.screens.converter

sealed interface ExchangeRatesUiState {
    data object Success: ExchangeRatesUiState
    data object Error: ExchangeRatesUiState
    data object Loading: ExchangeRatesUiState
}