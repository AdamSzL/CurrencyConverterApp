package com.example.currencyconverterapp.ui.screens.converter

import com.example.currencyconverterapp.data.defaultAvailableCurrencies
import com.example.currencyconverterapp.data.defaultBaseCurrency
import com.example.currencyconverterapp.data.defaultBaseCurrencyValue
import com.example.currencyconverterapp.data.defaultExchangeRates
import com.example.currencyconverterapp.data.defaultTargetCurrency
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.ExchangeRate

data class ConverterUiState(
    val baseCurrency: Currency = defaultBaseCurrency,
    val baseCurrencyValue: Double = defaultBaseCurrencyValue,
    val selectedTargetCurrency: Currency? = null,
    val availableCurrencies: List<Currency> = defaultAvailableCurrencies,
    val exchangeRates: List<ExchangeRate> = defaultExchangeRates,
    val selectedConversionEntryCodes: List<String> = listOf(),
    val isSelectionModeEnabled: Boolean = false,
)