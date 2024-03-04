package com.example.currencyconverterapp.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrenciesApiResponse(
    val data: Map<String, Currency>
)