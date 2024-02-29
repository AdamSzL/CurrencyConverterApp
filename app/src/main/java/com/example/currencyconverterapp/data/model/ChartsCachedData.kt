package com.example.currencyconverterapp.data.model

import com.example.currencyconverterapp.data.util.defaultBaseCurrency
import com.example.currencyconverterapp.data.util.defaultTargetCurrency
import kotlinx.serialization.Serializable

@Serializable
data class ChartsCachedData(
    val baseCurrency: Currency = defaultBaseCurrency,
    val targetCurrency: Currency = defaultTargetCurrency,
    val isColumnChartEnabled: Boolean = false,
    val selectedTimePeriod: TimePeriod = TimePeriod.ONE_MONTH,
    val historicalExchangeRates: List<DateTimeExchangeRatesInfo> = emptyList(),
)