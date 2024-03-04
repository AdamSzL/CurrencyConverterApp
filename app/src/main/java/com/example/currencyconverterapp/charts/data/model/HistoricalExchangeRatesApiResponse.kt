package com.example.currencyconverterapp.charts.data.model

import kotlinx.serialization.Serializable

@Serializable
data class HistoricalExchangeRatesApiResponse(
    val data: List<DateTimeExchangeRatesInfo>
)
