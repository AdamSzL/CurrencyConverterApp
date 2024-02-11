package com.example.currencyconverterapp.ui.screens.charts

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus

data class TimePeriodUnits(val years: Int, val months: Int, val days: Int)

object DateTimeHandler {

    private fun getCurrentInstant() = Clock.System.now()

    fun getCurrentDate(): String = "${getCurrentInstant().toString().substringBeforeLast('.')}Z"

    fun getUnitsToSubtractFromTimePeriod(timePeriod: TimePeriod): TimePeriodUnits {
        val years = if (timePeriod == TimePeriod.ONE_YEAR) 1 else 0
        val months = when (timePeriod) {
            TimePeriod.SIX_MONTHS -> 6
            TimePeriod.THREE_MONTHS -> 3
            TimePeriod.ONE_MONTH -> 1
            else -> 0
        }
        val days = when (timePeriod) {
            TimePeriod.TWO_WEEKS -> 14
            TimePeriod.ONE_WEEK -> 7
            TimePeriod.TODAY -> 1
            else -> 0
        }
        return TimePeriodUnits(years, months, days)
    }

    fun subtractTimePeriodFromDate(date: String, timePeriod: TimePeriod): String {
        val instant = Instant.parse(date)
        val timeZone = TimeZone.currentSystemDefault()
        val (years, months, days) = getUnitsToSubtractFromTimePeriod(timePeriod)
        val result = instant.minus(DateTimePeriod(years = years, months = months, days = days), timeZone).toString()
        if (timePeriod == TimePeriod.TODAY) {
            val localDateTime = LocalDateTime.parse(result.dropLast(1))
            val newDate = LocalDateTime(localDateTime.year, localDateTime.monthNumber, localDateTime.dayOfMonth, hour = 23, minute = 59, second = 59)
            return "${newDate}Z"
        }
        return result
    }

    fun formatDateByTimePeriod(date: String, timePeriod: TimePeriod): String {
        val localDateTime = LocalDateTime.parse(date.dropLast(1))
        return if (timePeriod == TimePeriod.TODAY) {
            "${formatDigit(localDateTime.hour)}:${formatDigit(localDateTime.minute)}:${formatDigit(localDateTime.second)}"
        } else {
            "${localDateTime.year}-${formatDigit(localDateTime.monthNumber)}-${formatDigit(localDateTime.dayOfMonth)}"
        }
    }

    fun formatDigit(digit: Int): String = if (digit < 10) "0${digit}" else digit.toString()
}

enum class TimePeriod(val label: String) {
    ONE_YEAR("1 Year"),
    SIX_MONTHS("6 Months"),
    THREE_MONTHS("3 Months"),
    ONE_MONTH("1 Month"),
    TWO_WEEKS("2 Weeks"),
    ONE_WEEK("1 Week"),
    TODAY("Today");

    companion object {
        fun getByLabel(label: String): TimePeriod {
            return entries.find { it.label == label } ?: ONE_YEAR
        }
    }
}
