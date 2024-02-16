package com.example.currencyconverterapp.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrenciesCachedData(
    val currencies: List<Currency> = emptyList(),
)