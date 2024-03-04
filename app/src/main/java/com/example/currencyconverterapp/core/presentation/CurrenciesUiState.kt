package com.example.currencyconverterapp.core.presentation

import com.example.currencyconverterapp.core.data.model.Currency

sealed interface CurrenciesUiState {
    data class Success(val currencies: List<Currency>): CurrenciesUiState
    data object Error: CurrenciesUiState
    data object Loading: CurrenciesUiState
}
