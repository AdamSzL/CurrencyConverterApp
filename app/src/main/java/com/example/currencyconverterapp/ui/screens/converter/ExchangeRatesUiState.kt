package com.example.currencyconverterapp.ui.screens.converter

import com.example.currencyconverterapp.model.ExchangeRate

sealed interface ExchangeRatesUiState {
    data class Success(val exchangeRates: List<ExchangeRate>): ExchangeRatesUiState
    data object Error: ExchangeRatesUiState
    data object Loading: ExchangeRatesUiState
}