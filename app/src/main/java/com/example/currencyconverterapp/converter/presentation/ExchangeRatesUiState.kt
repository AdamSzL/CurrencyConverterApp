package com.example.currencyconverterapp.converter.presentation

sealed interface ExchangeRatesUiState {
    data object Success: ExchangeRatesUiState
    data object Error: ExchangeRatesUiState
    data object Loading: ExchangeRatesUiState
}