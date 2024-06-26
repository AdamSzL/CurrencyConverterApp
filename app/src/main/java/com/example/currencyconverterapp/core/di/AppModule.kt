package com.example.currencyconverterapp.core.di

import com.example.currencyconverterapp.BuildConfig
import com.example.currencyconverterapp.core.data.network.CurrencyConverterApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL =
        "https://api.currencyapi.com/v3/"


    private val json = Json { ignoreUnknownKeys = true }

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

    @Provides
    @Singleton
    fun provideCurrencyConverterApi(): CurrencyConverterApiService {
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
            .create(CurrencyConverterApiService::class.java)
    }
}