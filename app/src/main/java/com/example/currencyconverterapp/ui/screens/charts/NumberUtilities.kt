package com.example.currencyconverterapp.ui.screens.charts

object NumberUtilities {
    fun getRoundingByDifference(difference: Float): Int {
        return if (difference > 1) {
            2
        } else {
            val decimalString = difference.toBigDecimal().toPlainString().substringAfter('.', "0")
            decimalString.indexOfFirst { it != '0' } + 2
        }
    }
}