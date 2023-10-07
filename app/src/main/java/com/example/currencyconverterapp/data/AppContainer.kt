package com.example.currencyconverterapp.data

import com.example.currencyconverterapp.network.CurrencyConverterApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {

}

class DefaultAppContainer: AppContainer {
    private val baseUrl =
        "https://api.currencyapi.com/v3/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: CurrencyConverterApiService by lazy {
        retrofit.create(CurrencyConverterApiService::class.java)
    }
}