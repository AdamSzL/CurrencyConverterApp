package com.example.currencyconverterapp.network

import com.example.currencyconverterapp.model.CurrenciesApiResponse
import retrofit2.http.GET

interface CurrencyConverterApiService {
    @GET("currencies")
    suspend fun getCurrencies(): CurrenciesApiResponse
}