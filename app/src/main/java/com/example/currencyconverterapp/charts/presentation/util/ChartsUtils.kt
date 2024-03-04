package com.example.currencyconverterapp.charts.presentation.util

import com.example.currencyconverterapp.charts.presentation.ChartsUiState
import com.example.currencyconverterapp.core.data.model.Currency

object ChartsUtils {
    fun filterChartCurrencies(currencies: List<Currency>, chartsUiState: ChartsUiState): List<Currency> {
        return currencies.filter { currency ->
            currency.code != chartsUiState.selectedBaseCurrency.code &&
                    currency.code != chartsUiState.selectedTargetCurrency.code
        }
    }

}