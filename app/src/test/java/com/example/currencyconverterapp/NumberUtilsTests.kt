package com.example.currencyconverterapp

import com.example.currencyconverterapp.charts.presentation.util.NumberUtils.getRoundingByDifference
import org.junit.Assert.assertEquals
import org.junit.Test

class NumberUtilsTests {

    @Test
    fun getRoundingByDifference_differenceGreaterThanOne_returnsCorrectRounding() {
        assertEquals(2, getRoundingByDifference(12.34f))
    }

    @Test
    fun getRoundingByDifference_differenceLessThanOne_returnsCorrectRounding() {
        assertEquals(4, getRoundingByDifference(0.0239f))
    }

    @Test
    fun getRoundingByDifference_differenceVerySmall_returnsCorrectRounding() {
        assertEquals(6, getRoundingByDifference(0.000282985f))
    }
}