package com.example.currencyconverterapp.fake

import com.example.currencyconverterapp.core.data.model.CurrenciesApiResponse
import com.example.currencyconverterapp.charts.data.model.HistoricalExchangeRatesApiResponse
import com.example.currencyconverterapp.core.data.model.LatestExchangeRatesApiResponse
import com.example.currencyconverterapp.core.data.network.CurrencyConverterApiService

class FakeCurrencyConverterApiService: CurrencyConverterApiService {
    override suspend fun getCurrencies(): CurrenciesApiResponse {
        return FakeDataSource.currenciesApiResponse
    }

    override suspend fun getLatestExchangeRates(
        baseCurrency: String,
        currencies: String
    ): LatestExchangeRatesApiResponse {
        return FakeDataSource.latestExchangeRatesApiResponse
    }

    override suspend fun getHistoricalExchangeRates(
        dateTimeStart: String,
        dateTimeEnd: String,
        baseCurrency: String,
        currencies: String,
        accuracy: String
    ): HistoricalExchangeRatesApiResponse {
        return FakeDataSource.historicalExchangeRatesApiResponse
    }
}