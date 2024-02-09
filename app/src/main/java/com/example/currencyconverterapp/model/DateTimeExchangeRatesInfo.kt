package com.example.currencyconverterapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DateTimeExchangeRatesInfo(
    val datetime: String,
    @SerialName("currencies")
    val exchangeRatesData: Map<String, ExchangeRate>
)