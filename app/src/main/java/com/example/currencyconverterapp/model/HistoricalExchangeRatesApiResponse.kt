package com.example.currencyconverterapp.model

import kotlinx.serialization.Serializable

@Serializable
data class HistoricalExchangeRatesApiResponse(
    val data: List<DateTimeExchangeRatesInfo>
)
