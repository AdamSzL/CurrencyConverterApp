package com.example.currencyconverterapp

import com.example.currencyconverterapp.charts.data.model.RecentTimePeriod
import com.example.currencyconverterapp.charts.data.model.TimePeriodType
import com.example.currencyconverterapp.charts.presentation.util.DateTimeUtils.convertDateTimeToDate
import com.example.currencyconverterapp.charts.presentation.util.DateTimeUtils.formatDateByTimePeriodType
import com.example.currencyconverterapp.charts.presentation.util.DateTimeUtils.formatDigit
import com.example.currencyconverterapp.charts.presentation.util.DateTimeUtils.getAndFormatTimeDifference
import com.example.currencyconverterapp.charts.presentation.util.DateTimeUtils.getCurrentDate
import com.example.currencyconverterapp.charts.presentation.util.DateTimeUtils.getDateFromMilliseconds
import com.example.currencyconverterapp.charts.presentation.util.DateTimeUtils.getUnitsToSubtractFromTimePeriod
import com.example.currencyconverterapp.charts.presentation.util.DateTimeUtils.subtractTimePeriodFromDate
import com.example.currencyconverterapp.charts.presentation.util.TimePeriodUnits
import kotlinx.datetime.LocalDateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DateTimeUtilsTests {

    private val date = "2022-02-10T15:59:59Z"

    @Test
    fun getCurrentDate_returnsCorrectFormat() {
        val isoDateRegex = Regex("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}Z")
        assertTrue(getCurrentDate().matches(isoDateRegex))
    }

    @Test
    fun getUnitsToSubtractFromTimePeriod_timePeriodTwoWeeks_returnsCorrectUnits() {
        val result = getUnitsToSubtractFromTimePeriod(RecentTimePeriod.TWO_WEEKS)
        assertEquals(TimePeriodUnits(0, 0, 14), result)
    }

    @Test
    fun getUnitsToSubtractFromTimePeriod_timePeriodToday_returnsCorrectUnits() {
        val result = getUnitsToSubtractFromTimePeriod(RecentTimePeriod.ONE_DAY)
        assertEquals(TimePeriodUnits(0, 0, 1), result)
    }

    @Test
    fun getUnitsToSubtractFromTimePeriod_timePeriodSixMonths_returnsCorrectUnits() {
        val result = getUnitsToSubtractFromTimePeriod(RecentTimePeriod.SIX_MONTHS)
        assertEquals(TimePeriodUnits(0, 6, 0), result)
    }

    @Test
    fun getUnitsToSubtractFromTimePeriod_timePeriodThreeMonths_returnsCorrectUnits() {
        val result = getUnitsToSubtractFromTimePeriod(RecentTimePeriod.THREE_MONTHS)
        assertEquals(TimePeriodUnits(0, 3, 0), result)
    }

    @Test
    fun subtractTimePeriodFromDate_timePeriodOneYear_returnsCorrectDate() {
        val result = subtractTimePeriodFromDate(date, RecentTimePeriod.ONE_YEAR)
        val dateTime = LocalDateTime.parse(result.dropLast(1))
        assertEquals(2021, dateTime.year)
        assertEquals(2, dateTime.monthNumber)
        assertEquals(10, dateTime.dayOfMonth)
    }

    @Test
    fun subtractTimePeriodFromDate_timePeriodSixMonths_returnsCorrectDate() {
        val result = subtractTimePeriodFromDate(date, RecentTimePeriod.SIX_MONTHS)
        val dateTime = LocalDateTime.parse(result.dropLast(1))
        assertEquals(2021, dateTime.year)
        assertEquals(8, dateTime.monthNumber)
        assertEquals(10, dateTime.dayOfMonth)
    }

    @Test
    fun subtractTimePeriodFromDate_timePeriodThreeMonths_returnsCorrectDate() {
        val result = subtractTimePeriodFromDate(date, RecentTimePeriod.THREE_MONTHS)
        val dateTime = LocalDateTime.parse(result.dropLast(1))
        assertEquals(2021, dateTime.year)
        assertEquals(11, dateTime.monthNumber)
        assertEquals(10, dateTime.dayOfMonth)
    }

    @Test
    fun subtractTimePeriodFromDate_timePeriodOneMonth_returnsCorrectDate() {
        val result = subtractTimePeriodFromDate(date, RecentTimePeriod.ONE_MONTH)
        val dateTime = LocalDateTime.parse(result.dropLast(1))
        assertEquals(2022, dateTime.year)
        assertEquals(1, dateTime.monthNumber)
        assertEquals(10, dateTime.dayOfMonth)
    }

    @Test
    fun subtractTimePeriodFromDate_timePeriodTwoWeeks_returnCorrectDate() {
        val result = subtractTimePeriodFromDate(date, RecentTimePeriod.TWO_WEEKS)
        val dateTime = LocalDateTime.parse(result.dropLast(1))
        assertEquals(2022, dateTime.year)
        assertEquals(1, dateTime.monthNumber)
        assertEquals(27, dateTime.dayOfMonth)
    }

    @Test
    fun subtractTimePeriodFromDate_timePeriodToday_returnCorrectDate() {
        val result = subtractTimePeriodFromDate(date, RecentTimePeriod.ONE_DAY)
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
        assertEquals("23:59:59", formatDateByTimePeriodType("2022-01-04T23:59:59Z", TimePeriodType.Recent(RecentTimePeriod.ONE_DAY)))
    }

    @Test
    fun formatDateByTimePeriod_periodOneYear_returnsDate() {
        assertEquals("2022-01-04", formatDateByTimePeriodType("2022-01-04T23:59:59Z", TimePeriodType.Recent(RecentTimePeriod.ONE_YEAR)))
    }

    @Test
    fun getAndFormatTimeDifference_dateOneHourTwoMinutesThreeSecondsAgo_returnsCorrectDifferenceInCorrectFormat() {
        assertEquals("1 hour(s) 2 minute(s) 3 second(s) ago", getAndFormatTimeDifference("2024-02-22T16:51:50Z", "2024-02-22T17:53:53Z"))
    }

    @Test
    fun getAndFormatTimeDifference_dateTwoDaysTenMinutesAgo_returnsCorrectDifferenceInCorrectFormat() {
        assertEquals("2 day(s) 10 minute(s) ago", getAndFormatTimeDifference("2024-02-20T17:43:53Z", "2024-02-22T17:53:53Z"))
    }

    @Test
    fun getAndFormatTimeDifference_dateNow_returnsNow() {
        assertEquals("just now", getAndFormatTimeDifference("2024-02-20T17:43:53Z", "2024-02-20T17:43:53Z"))
    }

    @Test
    fun getDateFromMilliseconds_millisNow_returnsCorrectDate() {
        assertEquals("2024-03-16", getDateFromMilliseconds(1710598286412))
    }

    @Test
    fun convertDateTimeToDate_dateTimeNow_returnsCorrectDatePart() {
        assertEquals("2023-12-16", convertDateTimeToDate("2023-12-16T14:48:20Z"))
    }
}