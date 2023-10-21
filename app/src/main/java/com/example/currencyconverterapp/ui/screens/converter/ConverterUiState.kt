package com.example.currencyconverterapp.ui.screens.converter

import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.ExchangeRate

data class ConverterUiState(
    val baseCurrency: Currency = defaultBaseCurrency,
    val baseCurrencyValue: Double = defaultBaseCurrencyValue,
    val selectedTargetCurrency: Currency = defaultBaseCurrency,
    val availableCurrencies: List<Currency> = defaultAvailableCurrencies,
    val exchangeRates: List<ExchangeRate> = defaultExchangeRates,
    val selectedConversionEntryCodes: List<String> = listOf(),
    val isSelectionModeEnabled: Boolean = false,
)