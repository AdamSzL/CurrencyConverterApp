package com.example.currencyconverterapp.charts.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class TimePeriod(val label: String) {
    ONE_YEAR("1 Year"),
    SIX_MONTHS("6 Months"),
    THREE_MONTHS("3 Months"),
    ONE_MONTH("1 Month"),
    TWO_WEEKS("2 Weeks"),
    ONE_WEEK("1 Week"),
    ONE_DAY("Last 24 Hours");

    companion object {
        fun getByLabel(label: String): TimePeriod {
            return entries.find { it.label == label } ?: ONE_YEAR
        }
    }
}
