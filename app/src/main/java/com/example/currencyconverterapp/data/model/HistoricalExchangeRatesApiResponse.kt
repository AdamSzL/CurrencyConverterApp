package com.example.currencyconverterapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class HistoricalExchangeRatesApiResponse(
    val data: List<DateTimeExchangeRatesInfo>
)
