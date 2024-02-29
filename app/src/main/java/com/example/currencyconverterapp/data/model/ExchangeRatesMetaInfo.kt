package com.example.currencyconverterapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRatesMetaInfo(
    @SerialName("last_updated_at")
    val lastUpdatedAt: String,
)
