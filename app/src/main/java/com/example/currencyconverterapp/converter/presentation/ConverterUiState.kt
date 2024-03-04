package com.example.currencyconverterapp.converter.presentation

import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.data.model.ExchangeRate
import com.example.currencyconverterapp.core.data.util.defaultBaseCurrency
import com.example.currencyconverterapp.core.data.util.defaultBaseCurrencyValue

data class ConverterUiState(
    val baseCurrency: Currency = defaultBaseCurrency,
    val baseCurrencyValue: Double = defaultBaseCurrencyValue,
    val selectedTargetCurrency: Currency? = null,
    val deletedExchangeRate: ExchangeRate? = null,
    val exchangeRates: List<ExchangeRate> = emptyList(),
    val exchangeRatesUiState: ExchangeRatesUiState = ExchangeRatesUiState.Loading,
    val shouldShowErrorMessage: Boolean = true,
)