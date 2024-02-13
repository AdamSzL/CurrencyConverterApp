package com.example.currencyconverterapp.ui.screens.converter

import com.example.currencyconverterapp.model.Currency

sealed interface CurrenciesUiState {
    data class Success(val currencies: List<Currency>): CurrenciesUiState
    data object Error: CurrenciesUiState
    data object Loading: CurrenciesUiState
}
