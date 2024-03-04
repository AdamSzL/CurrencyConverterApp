package com.example.currencyconverterapp.charts.data.model

import com.example.currencyconverterapp.core.data.model.ExchangeRate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DateTimeExchangeRatesInfo(
    val datetime: String,
    @SerialName("currencies")
    val exchangeRatesData: Map<String, ExchangeRate>
)