package com.example.currencyconverterapp.di

import com.example.currencyconverterapp.data.repository.ChartsCachedDataRepository
import com.example.currencyconverterapp.data.repository.ChartsCachedDataRepositoryImpl
import com.example.currencyconverterapp.data.repository.ConverterCachedDataRepository
import com.example.currencyconverterapp.data.repository.ConverterCachedDataRepositoryImpl
import com.example.currencyconverterapp.data.repository.CurrenciesRepository
import com.example.currencyconverterapp.data.repository.CurrenciesRepositoryImpl
import com.example.currencyconverterapp.data.repository.HistoricalExchangeRatesRepository
import com.example.currencyconverterapp.data.repository.HistoricalExchangeRatesRepositoryImpl
import com.example.currencyconverterapp.data.repository.LatestExchangeRatesRepository
import com.example.currencyconverterapp.data.repository.LatestExchangeRatesRepositoryImpl
import com.example.currencyconverterapp.data.repository.WatchlistDataRepository
import com.example.currencyconverterapp.data.repository.WatchlistDataRepositoryImpl
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
    abstract fun bindCurrenciesRepository(
        currenciesRepositoryImpl: CurrenciesRepositoryImpl,
    ): CurrenciesRepository

    @Binds
    @Singleton
    abstract fun bindLatestExchangeRatesRepository(
        latestExchangeRatesRepositoryImpl: LatestExchangeRatesRepositoryImpl,
    ): LatestExchangeRatesRepository

    @Binds
    @Singleton
    abstract fun bindHistoricalExchangeRatesRepository(
        historicalExchangeRatesRepositoryImpl: HistoricalExchangeRatesRepositoryImpl,
    ): HistoricalExchangeRatesRepository

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
    abstract fun bindWatchlistDataRepository(
        watchlistDataRepositoryImpl: WatchlistDataRepositoryImpl
    ): WatchlistDataRepository
}