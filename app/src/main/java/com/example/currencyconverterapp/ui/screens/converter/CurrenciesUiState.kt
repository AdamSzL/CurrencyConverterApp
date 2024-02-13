package com.example.currencyconverterapp.ui.screens.converter

import com.example.currencyconverterapp.model.Currency

sealed interface CurrenciesUiState {
    data class Success(val currencies: List<Currency>): CurrenciesUiState
    object Error: CurrenciesUiState
    object Loading: CurrenciesUiState
}
