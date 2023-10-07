package com.example.currencyconverterapp.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrenciesApiResponse(
    val data: Map<String, Currency>
)