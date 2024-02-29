package com.example.currencyconverterapp.ui.screens.converter

import com.example.currencyconverterapp.data.model.Currency
import com.example.currencyconverterapp.data.model.ExchangeRate
import com.example.currencyconverterapp.data.util.defaultBaseCurrency
import com.example.currencyconverterapp.data.util.defaultBaseCurrencyValue

data class ConverterUiState(
    val baseCurrency: Currency = defaultBaseCurrency,
    val baseCurrencyValue: Double = defaultBaseCurrencyValue,
    val selectedTargetCurrency: Currency? = null,
    val deletedExchangeRate: ExchangeRate? = null,
    val exchangeRates: List<ExchangeRate> = emptyList(),
    val exchangeRatesUiState: ExchangeRatesUiState = ExchangeRatesUiState.Loading,
    val shouldShowErrorMessage: Boolean = true,
)