package com.example.currencyconverterapp.data.model

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class WatchlistItem(
    val id: String = UUID.randomUUID().toString(),
    val baseCurrency: Currency,
    val targetCurrency: Currency,
    val targetValue: Double,
    val exchangeRateRelation: ExchangeRateRelation,
)
