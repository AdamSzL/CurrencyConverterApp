package com.example.currencyconverterapp.charts.data.repository

import com.example.currencyconverterapp.charts.data.model.HistoricalExchangeRatesApiResponse
import com.example.currencyconverterapp.core.data.network.CurrencyConverterApiService
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
