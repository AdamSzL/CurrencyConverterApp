package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.model.HistoricalExchangeRatesApiResponse
import com.example.currencyconverterapp.data.network.CurrencyConverterApiService
import javax.inject.Inject

interface HistoricalExchangeRatesRepository {
    suspend fun getHistoricalExchangeRates(dateTimeStart: String = "", dateTimeEnd: String = "", baseCurrency: String = "", currencies: String = "", accuracy: String = ""): HistoricalExchangeRatesApiResponse
}

class HistoricalExchangeRatesRepositoryImpl @Inject constructor(
    private val currencyConverterApiService: CurrencyConverterApiService,
): HistoricalExchangeRatesRepository {

    override suspend fun getHistoricalExchangeRates(
        dateTimeStart: String,
        dateTimeEnd: String,
        baseCurrency: String,
        currencies: String,
        accuracy: String,
    ): HistoricalExchangeRatesApiResponse =
        currencyConverterApiService.getHistoricalExchangeRates(dateTimeStart, dateTimeEnd, baseCurrency, currencies, accuracy)
}
