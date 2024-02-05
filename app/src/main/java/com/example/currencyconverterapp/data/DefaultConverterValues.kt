package com.example.currencyconverterapp.data

import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.ExchangeRate
import com.example.currencyconverterapp.ui.screens.converter.BaseCurrencyData

val defaultBaseCurrency = Currency(
    symbol = "€",
    name = "Euro",
    symbolNative = "€",
    decimalDigits = 2,
    rounding = 0,
    code =  "EUR",
    namePlural = "Euros"
)

val defaultAvailableCurrencies: List<Currency> = listOf(
    Currency(
        symbol = "£",
        name = "British Pound Sterling",
        symbolNative = "£",
        decimalDigits = 2,
        rounding = 0,
        code = "GBP",
        namePlural = "British pounds sterling"
    ),
    Currency(
        symbol = "zł",
        name = "Polish Zloty",
        symbolNative ="zł",
        decimalDigits = 2,
        rounding = 0,
        code = "PLN",
        namePlural = "Polish zlotys",
    ),
    Currency(
        symbol = "$",
        name = "US Dollar",
        symbolNative = "$",
        decimalDigits = 2,
        rounding = 0,
        code = "USD",
        namePlural = "US dollars"
    ),
) + defaultBaseCurrency

val defaultBaseCurrencyValue = 1.23

val defaultExchangeRates: List<ExchangeRate> = listOf(
    ExchangeRate(
        code = "USD",
        value = 1.23,
    ),
    ExchangeRate(
        code = "PLN",
        value = 4.45,
    ),
)

val defaultTargetCurrency = Currency(
    symbol = "$",
    name = "US Dollar",
    symbolNative = "$",
    decimalDigits = 2,
    rounding = 0,
    code = "USD",
    namePlural = "US dollars"
)

val defaultBaseCurrencyData = BaseCurrencyData(
    currencies = defaultAvailableCurrencies,
    baseCurrency = defaultBaseCurrency,
    baseCurrencyValue = defaultBaseCurrencyValue
)