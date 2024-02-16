package com.example.currencyconverterapp.model

import com.example.currencyconverterapp.data.defaultBaseCurrency
import com.example.currencyconverterapp.data.defaultTargetCurrency
import kotlinx.serialization.Serializable

@Serializable
data class ChartsCachedData(
    val baseCurrency: Currency = defaultBaseCurrency,
    val targetCurrency: Currency = defaultTargetCurrency,
    val isColumnChartEnabled: Boolean = false,
    val selectedTimePeriod: TimePeriod = TimePeriod.ONE_MONTH,
    val historicalExchangeRates: List<DateTimeExchangeRatesInfo> = emptyList(),
)