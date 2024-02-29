package com.example.currencyconverterapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LatestExchangeRatesApiResponse(
    val meta: ExchangeRatesMetaInfo,
    val data: Map<String, ExchangeRate>
)
