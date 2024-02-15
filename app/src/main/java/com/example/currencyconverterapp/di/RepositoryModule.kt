package com.example.currencyconverterapp.di

import com.example.currencyconverterapp.data.CurrenciesUserPreferencesRepository
import com.example.currencyconverterapp.data.CurrenciesUserPreferencesRepositoryImpl
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
    abstract fun bindCurrenciesUserPreferencesRepository(
        currenciesUserPreferencesRepositoryImpl: CurrenciesUserPreferencesRepositoryImpl
    ): CurrenciesUserPreferencesRepository
}