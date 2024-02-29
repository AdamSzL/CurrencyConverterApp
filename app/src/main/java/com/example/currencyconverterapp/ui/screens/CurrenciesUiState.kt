package com.example.currencyconverterapp.ui.screens

import com.example.currencyconverterapp.data.model.Currency

sealed interface CurrenciesUiState {
    data class Success(val currencies: List<Currency>): CurrenciesUiState
    data object Error: CurrenciesUiState
    data object Loading: CurrenciesUiState
}
