package com.example.currencyconverterapp.converter.data.model

import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.data.model.ExchangeRate
import com.example.currencyconverterapp.core.data.util.defaultBaseCurrency
import kotlinx.serialization.Serializable

@Serializable
data class ConverterCachedData(
    val baseCurrency: Currency = defaultBaseCurrency,
    val baseCurrencyValue: String = "1.00",
    val exchangeRates: List<ExchangeRate> = listOf()
)
