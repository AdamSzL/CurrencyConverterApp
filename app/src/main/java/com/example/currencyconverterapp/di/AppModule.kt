package com.example.currencyconverterapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.currencyconverterapp.BuildConfig
import com.example.currencyconverterapp.data.ChartsCachedDataSerializer
import com.example.currencyconverterapp.data.ConverterCachedDataSerializer
import com.example.currencyconverterapp.data.CurrenciesCachedDataSerializer
import com.example.currencyconverterapp.model.ChartsCachedData
import com.example.currencyconverterapp.model.ConverterCachedData
import com.example.currencyconverterapp.model.CurrenciesCachedData
import com.example.currencyconverterapp.network.CurrencyConverterApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    private const val CONVERTER_DATA_STORE = "converter_data_store"

    private const val CHARTS_DATA_STORE = "charts_data_store"

    private const val CURRENCIES_DATA_STORE = "currencies_data_store"

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

    @Provides
    @Singleton
    fun provideConverterCachedDataStore(@ApplicationContext appContext: Context): DataStore<ConverterCachedData> {
        return DataStoreFactory.create(
            serializer = ConverterCachedDataSerializer,
        ) {
            appContext.dataStoreFile(CONVERTER_DATA_STORE)
        }
    }

    @Provides
    @Singleton
    fun provideChartsCachedDataStore(@ApplicationContext appContext: Context): DataStore<ChartsCachedData> {
        return DataStoreFactory.create(
            serializer = ChartsCachedDataSerializer,
        ) {
            appContext.dataStoreFile(CHARTS_DATA_STORE)
        }
    }

    @Provides
    @Singleton
    fun provideCurrenciesCachedDataStore(@ApplicationContext appContext: Context): DataStore<CurrenciesCachedData> {
        return DataStoreFactory.create(
            serializer = CurrenciesCachedDataSerializer,
        ) {
            appContext.dataStoreFile(CURRENCIES_DATA_STORE)
        }
    }
}