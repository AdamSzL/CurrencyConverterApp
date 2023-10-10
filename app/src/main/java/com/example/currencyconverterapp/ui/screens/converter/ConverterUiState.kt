package com.example.currencyconverterapp.ui.screens.converter

import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.ExchangeRate

data class ConverterUiState(
    val baseCurrency: Currency = defaultBaseCurrency,
    val targetCurrencies: List<Currency> = defaultTargetCurrencies,
    val availableCurrencies: List<Currency> = listOf(),
    val exchangeRates: List<ExchangeRate> = listOf(),
)