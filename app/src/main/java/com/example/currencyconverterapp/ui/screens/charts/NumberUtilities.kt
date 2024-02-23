package com.example.currencyconverterapp.ui.screens.charts

import java.math.RoundingMode

object NumberUtilities {
    fun getRoundingByDifference(difference: Float): Int {
        return if (difference > 1) {
            2
        } else {
            val decimalString = difference.toBigDecimal().toPlainString().substringAfter('.', "0")
            decimalString.indexOfFirst { it != '0' } + 2
        }
    }

    fun printDecimalWithoutScientificNotation(value: Double): String {
        return value.toBigDecimal().setScale(5, RoundingMode.HALF_EVEN).toPlainString()
    }
}