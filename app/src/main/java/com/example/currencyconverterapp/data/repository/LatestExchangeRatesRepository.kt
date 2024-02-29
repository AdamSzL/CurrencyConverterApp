package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.model.LatestExchangeRatesApiResponse
import com.example.currencyconverterapp.data.network.CurrencyConverterApiService
import javax.inject.Inject

interface LatestExchangeRatesRepository {
    suspend fun getLatestExchangeRates(baseCurrency: String = "", currencies: String = ""): LatestExchangeRatesApiResponse
}

class LatestExchangeRatesRepositoryImpl @Inject constructor(
    private val currencyConverterApiService: CurrencyConverterApiService,
): LatestExchangeRatesRepository {

    override suspend fun getLatestExchangeRates(
        baseCurrency: String,
        currencies: String
    ): LatestExchangeRatesApiResponse =
        currencyConverterApiService.getLatestExchangeRates(baseCurrency, currencies)
}
