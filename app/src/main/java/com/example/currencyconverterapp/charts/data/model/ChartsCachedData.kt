package com.example.currencyconverterapp.charts.data.model

import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.data.util.defaultBaseCurrency
import com.example.currencyconverterapp.core.data.util.defaultTargetCurrency
import kotlinx.serialization.Serializable

@Serializable
data class ChartsCachedData(
    val baseCurrency: Currency = defaultBaseCurrency,
    val targetCurrency: Currency = defaultTargetCurrency,
    val chartType: ChartType = ChartType.LINE,
    val selectedTimePeriodType: TimePeriodType = TimePeriodType.Recent(RecentTimePeriod.ONE_MONTH),
    val historicalExchangeRates: List<DateTimeExchangeRatesInfo> = emptyList(),
)