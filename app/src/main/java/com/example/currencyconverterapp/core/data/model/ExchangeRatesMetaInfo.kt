package com.example.currencyconverterapp.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRatesMetaInfo(
    @SerialName("last_updated_at")
    val lastUpdatedAt: String,
)
