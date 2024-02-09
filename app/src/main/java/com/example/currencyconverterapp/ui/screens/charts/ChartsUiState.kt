package com.example.currencyconverterapp.ui.screens.charts

import com.example.currencyconverterapp.data.defaultBaseCurrency
import com.example.currencyconverterapp.data.defaultHistoricalExchangeRates
import com.example.currencyconverterapp.data.defaultTargetCurrency
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.DateTimeExchangeRatesInfo
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.diff.ExtraStore
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class ChartsUiState(
    val selectedBaseCurrency: Currency = defaultBaseCurrency,
    val selectedTargetCurrency: Currency = defaultTargetCurrency,
    val isColumnChartEnabled: Boolean = false,
    val selectedTimePeriod: TimePeriod = TimePeriod.FIVE_YEARS,
)

enum class TimePeriod(val label: String) {
    FIVE_YEARS("5 Years"),
    ONE_YEAR("1 Year"),
    SIX_MONTHS("6 Months"),
    THREE_MONTHS("3 Months"),
    ONE_MONTH("1 Month"),
    TWO_WEEKS("2 Weeks"),
    ONE_WEEK("1 Week");

    companion object {
        fun getByLabel(label: String): TimePeriod {
            return values().find { it.label == label } ?: ONE_YEAR
        }
    }
}