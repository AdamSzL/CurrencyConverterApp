package com.example.currencyconverterapp.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LatestExchangeRatesApiResponse(
    val meta: ExchangeRatesMetaInfo,
    val data: Map<String, ExchangeRate>
)
