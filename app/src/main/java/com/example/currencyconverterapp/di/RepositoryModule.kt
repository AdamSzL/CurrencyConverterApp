package com.example.currencyconverterapp.di

import com.example.currencyconverterapp.data.ChartsCachedDataRepository
import com.example.currencyconverterapp.data.ChartsCachedDataRepositoryImpl
import com.example.currencyconverterapp.data.ConverterCachedDataRepository
import com.example.currencyconverterapp.data.ConverterCachedDataRepositoryImpl
import com.example.currencyconverterapp.data.CurrenciesCachedDataRepository
import com.example.currencyconverterapp.data.CurrenciesCachedDataRepositoryImpl
import com.example.currencyconverterapp.data.CurrencyConverterRepository
import com.example.currencyconverterapp.data.NetworkCurrencyConverterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCurrencyConverterRepository(
        networkCurrencyConverterRepository: NetworkCurrencyConverterRepository
    ): CurrencyConverterRepository

    @Binds
    @Singleton
    abstract fun bindConverterCachedDataRepository(
        converterCachedDataRepositoryImpl: ConverterCachedDataRepositoryImpl
    ): ConverterCachedDataRepository

    @Binds
    @Singleton
    abstract fun bindChartsCachedDataRepository(
        chartsCachedDataRepositoryImpl: ChartsCachedDataRepositoryImpl
    ): ChartsCachedDataRepository

    @Binds
    @Singleton
    abstract fun bindCurrenciesCachedDataRepository(
        currenciesCachedDataRepositoryImpl: CurrenciesCachedDataRepositoryImpl
    ): CurrenciesCachedDataRepository
}