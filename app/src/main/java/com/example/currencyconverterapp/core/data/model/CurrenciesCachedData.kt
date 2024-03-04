package com.example.currencyconverterapp.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrenciesCachedData(
    val currencies: List<Currency> = emptyList(),
)