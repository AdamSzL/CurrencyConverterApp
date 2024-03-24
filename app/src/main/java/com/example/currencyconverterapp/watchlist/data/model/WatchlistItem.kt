package com.example.currencyconverterapp.watchlist.data.model

import com.example.currencyconverterapp.core.data.model.Currency
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class WatchlistItem(
    val id: String = UUID.randomUUID().toString(),
    val baseCurrency: Currency,
    val targetCurrency: Currency,
    val targetValue: String,
    val exchangeRateRelation: ExchangeRateRelation,
)
