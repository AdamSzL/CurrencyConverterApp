package com.example.currencyconverterapp.data

import com.example.currencyconverterapp.model.CurrenciesApiResponse
import com.example.currencyconverterapp.network.CurrencyConverterApiService

interface CurrencyConverterRepository {
    suspend fun getCurrencies(): CurrenciesApiResponse
}

class NetworkCurrencyConverterRepository(
    private val currencyConverterApiService: CurrencyConverterApiService
): CurrencyConverterRepository {
    override suspend fun getCurrencies(): CurrenciesApiResponse = currencyConverterApiService.getCurrencies()
}