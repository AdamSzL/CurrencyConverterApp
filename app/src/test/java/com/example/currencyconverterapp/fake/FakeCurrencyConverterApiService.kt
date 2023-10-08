package com.example.currencyconverterapp.fake

import com.example.currencyconverterapp.model.CurrenciesApiResponse
import com.example.currencyconverterapp.model.LatestExchangeRatesApiResponse
import com.example.currencyconverterapp.network.CurrencyConverterApiService

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
}