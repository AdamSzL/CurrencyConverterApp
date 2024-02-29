package com.example.currencyconverterapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrenciesCachedData(
    val currencies: List<Currency> = emptyList(),
)