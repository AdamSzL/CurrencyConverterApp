package com.example.currencyconverterapp.di

import com.example.currencyconverterapp.data.ChartsUserPreferencesRepository
import com.example.currencyconverterapp.data.ChartsUserPreferencesRepositoryImpl
import com.example.currencyconverterapp.data.ConverterUserPreferencesRepository
import com.example.currencyconverterapp.data.ConverterUserPreferencesRepositoryImpl
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
    abstract fun bindConverterUserPreferencesRepository(
        converterUserPreferencesRepositoryImpl: ConverterUserPreferencesRepositoryImpl
    ): ConverterUserPreferencesRepository

    @Binds
    @Singleton
    abstract fun bindChartsUserPreferencesRepository(
        chartsUserPreferencesRepositoryImpl: ChartsUserPreferencesRepositoryImpl
    ): ChartsUserPreferencesRepository
}