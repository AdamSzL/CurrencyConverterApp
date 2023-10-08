package com.example.currencyconverterapp.network

import com.example.currencyconverterapp.model.CurrenciesApiResponse
import com.example.currencyconverterapp.model.LatestExchangeRatesApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyConverterApiService {
    @GET("currencies")
    suspend fun getCurrencies(): CurrenciesApiResponse

    @GET("latest")
    suspend fun getLatestExchangeRates(
        @Query("base_currency") baseCurrency: String,
        @Query("currencies") currencies: String
    ): LatestExchangeRatesApiResponse
}