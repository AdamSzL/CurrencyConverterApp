package com.example.currencyconverterapp

import com.example.currencyconverterapp.ui.screens.charts.DateTimeHandler.formatDateByTimePeriod
import com.example.currencyconverterapp.ui.screens.charts.DateTimeHandler.formatDigit
import com.example.currencyconverterapp.ui.screens.charts.DateTimeHandler.getCurrentDate
import com.example.currencyconverterapp.ui.screens.charts.DateTimeHandler.getUnitsToSubtractFromTimePeriod
import com.example.currencyconverterapp.ui.screens.charts.DateTimeHandler.subtractTimePeriodFromDate
import com.example.currencyconverterapp.ui.screens.charts.TimePeriod
import com.example.currencyconverterapp.ui.screens.charts.TimePeriodUnits
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DateTimeHandlerTests {

    private val date = "2022-02-10T15:59:59Z"

    @Test
    fun getCurrentDate_returnsCorrectFormat() {
        val isoDateRegex = Regex("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}Z")
        assertTrue(getCurrentDate().matches(isoDateRegex))
    }

    @Test
    fun getUnitsToSubtractFromTimePeriod_timePeriodTwoWeeks_returnsCorrectUnits() {
        val result = getUnitsToSubtractFromTimePeriod(TimePeriod.TWO_WEEKS)
        assertEquals(TimePeriodUnits(0, 0, 14), result)
    }

    @Test
    fun getUnitsToSubtractFromTimePeriod_timePeriodToday_returnsCorrectUnits() {
        val result = getUnitsToSubtractFromTimePeriod(TimePeriod.ONE_DAY)
        assertEquals(TimePeriodUnits(0, 0, 1), result)
    }

    @Test
    fun getUnitsToSubtractFromTimePeriod_timePeriodSixMonths_returnsCorrectUnits() {
        val result = getUnitsToSubtractFromTimePeriod(TimePeriod.SIX_MONTHS)
        assertEquals(TimePeriodUnits(0, 6, 0), result)
    }

    @Test
    fun getUnitsToSubtractFromTimePeriod_timePeriodThreeMonths_returnsCorrectUnits() {
        val result = getUnitsToSubtractFromTimePeriod(TimePeriod.THREE_MONTHS)
        assertEquals(TimePeriodUnits(0, 3, 0), result)
    }

    @Test
    fun subtractTimePeriodFromDate_timePeriodOneYear_returnsCorrectDate() {
        val result = subtractTimePeriodFromDate(date, TimePeriod.ONE_YEAR)
        val dateTime = LocalDateTime.parse(result.dropLast(1))
        assertEquals(2021, dateTime.year)
        assertEquals(2, dateTime.monthNumber)
        assertEquals(10, dateTime.dayOfMonth)
    }

    @Test
    fun subtractTimePeriodFromDate_timePeriodSixMonths_returnsCorrectDate() {
        val result = subtractTimePeriodFromDate(date, TimePeriod.SIX_MONTHS)
        val dateTime = LocalDateTime.parse(result.dropLast(1))
        assertEquals(2021, dateTime.year)
        assertEquals(8, dateTime.monthNumber)
        assertEquals(10, dateTime.dayOfMonth)
    }

    @Test
    fun subtractTimePeriodFromDate_timePeriodThreeMonths_returnsCorrectDate() {
        val result = subtractTimePeriodFromDate(date, TimePeriod.THREE_MONTHS)
        val dateTime = LocalDateTime.parse(result.dropLast(1))
        assertEquals(2021, dateTime.year)
        assertEquals(11, dateTime.monthNumber)
        assertEquals(10, dateTime.dayOfMonth)
    }

    @Test
    fun subtractTimePeriodFromDate_timePeriodOneMonth_returnsCorrectDate() {
        val result = subtractTimePeriodFromDate(date, TimePeriod.ONE_MONTH)
        val dateTime = LocalDateTime.parse(result.dropLast(1))
        assertEquals(2022, dateTime.year)
        assertEquals(1, dateTime.monthNumber)
        assertEquals(10, dateTime.dayOfMonth)
    }

    @Test
    fun subtractTimePeriodFromDate_timePeriodTwoWeeks_returnCorrectDate() {
        val result = subtractTimePeriodFromDate(date, TimePeriod.TWO_WEEKS)
        val dateTime = LocalDateTime.parse(result.dropLast(1))
        assertEquals(2022, dateTime.year)
        assertEquals(1, dateTime.monthNumber)
        assertEquals(27, dateTime.dayOfMonth)
    }

    @Test
    fun subtractTimePeriodFromDate_timePeriodToday_returnCorrectDate() {
        val result = subtractTimePeriodFromDate(date, TimePeriod.ONE_DAY)
        val dateTime = LocalDateTime.parse(result.dropLast(1))
        assertEquals(2022, dateTime.year)
        assertEquals(2, dateTime.monthNumber)
        assertEquals(9, dateTime.dayOfMonth)
        assertEquals(15, dateTime.hour)
        assertEquals(59, dateTime.minute)
        assertEquals(59, dateTime.second)
    }

    @Test
    fun formatDigit_oneDigit_addsZeroPrefix() {
        assertEquals("04", formatDigit(4))
    }

    @Test
    fun formatDigit_twoDigits_doesNothing() {
        assertEquals("12", formatDigit(12))
    }

    @Test
    fun formatDateByTimePeriod_periodToday_returnsTime() {
        assertEquals("23:59:59", formatDateByTimePeriod("2022-01-04T23:59:59Z", TimePeriod.ONE_DAY))
    }

    @Test
    fun formatDateByTimePeriod_periodOneYear_returnsDate() {
        assertEquals("2022-01-04", formatDateByTimePeriod("2022-01-04T23:59:59Z", TimePeriod.ONE_YEAR))
    }
}