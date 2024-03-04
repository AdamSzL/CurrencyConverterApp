package com.example.currencyconverterapp.charts.data.model

import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.data.util.defaultBaseCurrency
import com.example.currencyconverterapp.core.data.util.defaultTargetCurrency
import kotlinx.serialization.Serializable

@Serializable
data class ChartsCachedData(
    val baseCurrency: Currency = defaultBaseCurrency,
    val targetCurrency: Currency = defaultTargetCurrency,
    val isColumnChartEnabled: Boolean = false,
    val selectedTimePeriod: TimePeriod = TimePeriod.ONE_MONTH,
    val historicalExchangeRates: List<DateTimeExchangeRatesInfo> = emptyList(),
)