package com.example.currencyconverterapp.data

import com.example.currencyconverterapp.BuildConfig
import com.example.currencyconverterapp.network.CurrencyConverterApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface AppContainer {
    val currencyConverterRepository: CurrencyConverterRepository
}

class DefaultAppContainer: AppContainer {
    private val baseUrl =
        "https://api.currencyapi.com/v3/"

    private val apiKeyInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .header("apikey", BuildConfig.API_KEY)
            .build()
        chain.proceed(modifiedRequest)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()

    private val retrofitService: CurrencyConverterApiService by lazy {
        retrofit.create(CurrencyConverterApiService::class.java)
    }

    override val currencyConverterRepository: CurrencyConverterRepository by lazy {
        NetworkCurrencyConverterRepository(retrofitService)
    }
}