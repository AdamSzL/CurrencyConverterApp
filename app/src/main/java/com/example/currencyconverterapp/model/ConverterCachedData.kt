package com.example.currencyconverterapp.model

import com.example.currencyconverterapp.data.defaultBaseCurrency
import kotlinx.serialization.Serializable

@Serializable
data class ConverterCachedData(
    val baseCurrency: Currency = defaultBaseCurrency,
    val baseCurrencyValue: Double = 1.00,
    val exchangeRates: List<ExchangeRate> = listOf(
        ExchangeRate(
            code = "USD",
            value = null,
        ),
        ExchangeRate(
            code = "PLN",
            value = null,
        )
    )
)
