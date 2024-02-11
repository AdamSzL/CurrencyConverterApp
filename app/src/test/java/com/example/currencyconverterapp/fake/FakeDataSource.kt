package com.example.currencyconverterapp.fake

import com.example.currencyconverterapp.model.CurrenciesApiResponse
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.DateTimeExchangeRatesInfo
import com.example.currencyconverterapp.model.ExchangeRate
import com.example.currencyconverterapp.model.ExchangeRatesMetaInfo
import com.example.currencyconverterapp.model.HistoricalExchangeRatesApiResponse
import com.example.currencyconverterapp.model.LatestExchangeRatesApiResponse

object FakeDataSource {
    val currencies = listOf(
        Currency(
            symbol = "AED",
            name = "United Arab Emirates Dirham",
            symbolNative = "د.إ",
            decimalDigits = 2,
            rounding = 0,
            code = "AED",
            namePlural = "UAE dirhams"
        ),
        Currency(
            symbol = "Af",
            name = "Afghan Afghani",
            symbolNative = "؋",
            decimalDigits = 0,
            rounding = 0,
            code = "AFN",
            namePlural = "Afghan Afghanis"
        )
    )
    val currencyData = currencies.associateBy { it.code }
    val currenciesApiResponse = CurrenciesApiResponse(data = currencyData)

    val exchangeRatesMetaInfo = ExchangeRatesMetaInfo(lastUpdatedAt = "2023-06-23T10:15:59Z")
    val exchangeRates = listOf(
        ExchangeRate(
            code = "AED",
            value = 3.67306
        ),
        ExchangeRate(
            code = "AFN",
            value = 91.80254
        ),
        ExchangeRate(
            code = "ALL",
            value = 108.22904
        ),
        ExchangeRate(
            code = "AMD",
            value = 480.41659
        ),
    )
    val exchangeRatesData = exchangeRates.associateBy { it.code }
    val latestExchangeRatesApiResponse = LatestExchangeRatesApiResponse(
        meta = exchangeRatesMetaInfo,
        data = exchangeRatesData
    )

    val dateTimeExchangeRatesInfo = listOf(
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
    val historicalExchangeRatesApiResponse = HistoricalExchangeRatesApiResponse(
        dateTimeExchangeRatesInfo)
}