package com.example.currencyconverterapp.model

import kotlinx.serialization.Serializable

@Serializable
data class LatestExchangeRatesApiResponse(
    val meta: ExchangeRatesMetaInfo,
    val data: Map<String, ExchangeRate>
)
