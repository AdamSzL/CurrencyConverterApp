package com.example.currencyconverterapp.data

import com.example.currencyconverterapp.model.CurrenciesApiResponse
import com.example.currencyconverterapp.model.LatestExchangeRatesApiResponse
import com.example.currencyconverterapp.network.CurrencyConverterApiService
import javax.inject.Inject

interface CurrencyConverterRepository {
    suspend fun getCurrencies(): CurrenciesApiResponse

    suspend fun getLatestExchangeRates(baseCurrency: String = "", currencies: String = ""): LatestExchangeRatesApiResponse
}

class NetworkCurrencyConverterRepository @Inject constructor(
    private val currencyConverterApiService: CurrencyConverterApiService
): CurrencyConverterRepository {
    override suspend fun getCurrencies(): CurrenciesApiResponse = currencyConverterApiService.getCurrencies()

    override suspend fun getLatestExchangeRates(baseCurrency: String, currencies: String)
        = currencyConverterApiService.getLatestExchangeRates(baseCurrency, currencies)
}