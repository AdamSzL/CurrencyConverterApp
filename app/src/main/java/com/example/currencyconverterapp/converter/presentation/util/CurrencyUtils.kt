package com.example.currencyconverterapp.converter.presentation.util

import com.example.currencyconverterapp.core.data.model.Currency
import java.text.NumberFormat

object CurrencyUtils {
    fun getCurrencyFormat(currency: Currency): NumberFormat {
        val currencyFormat = NumberFormat.getCurrencyInstance()
        currencyFormat.maximumFractionDigits = 4
        val targetCurrencyInstance = java.util.Currency.getInstance(currency.code)
        currencyFormat.currency = targetCurrencyInstance
        return currencyFormat
    }

}