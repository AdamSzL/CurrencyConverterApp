package com.example.currencyconverterapp.ui.screens.converter

import com.example.currencyconverterapp.data.defaultBaseCurrency
import com.example.currencyconverterapp.data.defaultBaseCurrencyValue
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.ExchangeRate

data class ConverterUiState(
    val baseCurrency: Currency = defaultBaseCurrency,
    val baseCurrencyValue: Double = defaultBaseCurrencyValue,
    val selectedTargetCurrency: Currency? = null,
    val deletedExchangeRate: ExchangeRate? = null,
    val exchangeRatesUiState: ExchangeRatesUiState = ExchangeRatesUiState.Loading
)