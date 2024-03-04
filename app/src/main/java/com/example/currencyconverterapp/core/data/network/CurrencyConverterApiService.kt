package com.example.currencyconverterapp.core.data.network

import com.example.currencyconverterapp.charts.data.model.HistoricalExchangeRatesApiResponse
import com.example.currencyconverterapp.core.data.model.CurrenciesApiResponse
import com.example.currencyconverterapp.core.data.model.LatestExchangeRatesApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyConverterApiService {
    @GET("currencies")
    suspend fun getCurrencies(): CurrenciesApiResponse

    @GET("latest")
    suspend fun getLatestExchangeRates(
        @Query("base_currency") baseCurrency: String = "",
        @Query("currencies") currencies: String = ""
    ): LatestExchangeRatesApiResponse

    @GET("range")
    suspend fun getHistoricalExchangeRates(
        @Query("datetime_start") dateTimeStart: String = "",
        @Query("datetime_end") dateTimeEnd: String = "",
        @Query("base_currency") baseCurrency: String = "",
        @Query("currencies") currencies: String = "",
        @Query("accuracy") accuracy: String = ""
    ): HistoricalExchangeRatesApiResponse
}