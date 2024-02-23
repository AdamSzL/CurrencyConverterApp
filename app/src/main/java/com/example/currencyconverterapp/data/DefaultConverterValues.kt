package com.example.currencyconverterapp.data

import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.DateTimeExchangeRatesInfo
import com.example.currencyconverterapp.model.ExchangeRate
import com.example.currencyconverterapp.model.ExchangeRateRelation
import com.example.currencyconverterapp.model.WatchlistItem
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

val defaultBaseCurrencyValue = 1.00

val defaultTargetCurrency = Currency(
    symbol = "$",
    name = "US Dollar",
    symbolNative = "$",
    decimalDigits = 2,
    rounding = 0,
    code = "USD",
    namePlural = "US dollars"
)

val defaultWatchlistItems = listOf(
    WatchlistItem(
        baseCurrency = defaultBaseCurrency,
        targetCurrency = defaultTargetCurrency,
        targetValue = 1.22,
        exchangeRateRelation = ExchangeRateRelation.LESS_THAN,
    ),
    WatchlistItem(
        baseCurrency = defaultTargetCurrency,
        targetCurrency = defaultBaseCurrency,
        targetValue = 0.85,
        exchangeRateRelation = ExchangeRateRelation.GREATER_THAN_OR_EQUAL,
    )
)

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

val defaultBaseCurrencyData = BaseCurrencyData(
    currencies = defaultAvailableCurrencies,
    baseCurrency = defaultBaseCurrency,
    baseCurrencyValue = defaultBaseCurrencyValue
)

val defaultHistoricalExchangeRates = listOf(
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-01T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 1.05)
        )
    ),
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-02T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 1.10)
        )
    ),
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-03T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 1.07)
        )
    ),
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-04T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 1.03)
        )
    ),
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-05T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 1.00)
        )
    ),
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-06T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 0.96)
        )
    ),
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-07T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 0.87)
        )
    ),
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-08T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 1.05)
        )
    ),
)

val defaultHistoricalExchangeRatesNew = listOf(
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-01T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 0.90)
        )
    ),
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-02T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 0.85)
        )
    ),
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-03T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 0.80)
        )
    ),
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-04T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 1.03)
        )
    ),
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-05T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 1.00)
        )
    ),
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-06T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 0.96)
        )
    ),
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-07T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 0.87)
        )
    ),
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-08T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 1.05)
        )
    ),
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-01T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 0.90)
        )
    ),
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-02T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 0.85)
        )
    ),
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-03T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 0.80)
        )
    ),
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-04T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 1.03)
        )
    ),
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-05T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 1.00)
        )
    ),
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-06T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 0.96)
        )
    ),
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-07T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 0.87)
        )
    ),
    DateTimeExchangeRatesInfo(
        datetime = "2022-01-08T23:59:29Z",
        exchangeRatesData = mapOf(
            "EUR" to ExchangeRate("EUR", 1.05)
        )
    ),
)