package com.example.currencyconverterapp.charts.presentation.util

import com.example.currencyconverterapp.charts.data.model.TimePeriod
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.periodUntil


object DateTimeUtils {

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
            TimePeriod.ONE_DAY -> 1
            else -> 0
        }
        return TimePeriodUnits(years, months, days)
    }

    fun subtractTimePeriodFromDate(date: String, timePeriod: TimePeriod): String {
        val instant = Instant.parse(date)
        val timeZone = TimeZone.currentSystemDefault()
        val (years, months, days) = getUnitsToSubtractFromTimePeriod(timePeriod)
        return instant.minus(DateTimePeriod(years = years, months = months, days = days), timeZone).toString()
    }

    fun formatDateByTimePeriod(date: String, timePeriod: TimePeriod): String {
        val localDateTime = LocalDateTime.parse(date.dropLast(1))
        return if (timePeriod == TimePeriod.ONE_DAY) {
            "${formatDigit(localDateTime.hour)}:${formatDigit(localDateTime.minute)}:${formatDigit(localDateTime.second)}"
        } else {
            "${localDateTime.year}-${formatDigit(localDateTime.monthNumber)}-${formatDigit(localDateTime.dayOfMonth)}"
        }
    }

    fun formatDigit(digit: Int): String = if (digit < 10) "0${digit}" else digit.toString()

    fun getAndFormatTimeDifference(pastDate: String, currentDate: String = Clock.System.now().toString()): String {
        val instant = Instant.parse(pastDate)
        val period: DateTimePeriod = instant.periodUntil(Instant.parse(currentDate), TimeZone.UTC)
        val items = listOf(
            Pair("day(s)", period.days),
            Pair("hour(s)", period.hours),
            Pair("minute(s)", period.minutes),
            Pair("second(s)", period.seconds)
        )
        var result = ""
        var count = 0
        for (item in items) {
            if (item.second != 0) {
                result += "${if (count != 0) " " else ""}${item.second} ${item.first}"
                count++
            }
        }
        return if (result.isEmpty()) "just now" else "$result ago"
    }
}