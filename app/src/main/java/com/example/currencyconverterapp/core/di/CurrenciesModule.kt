package com.example.currencyconverterapp.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.currencyconverterapp.core.data.local.CurrenciesCachedDataSerializer
import com.example.currencyconverterapp.core.data.model.CurrenciesCachedData
import com.example.currencyconverterapp.core.data.repository.CurrenciesRepository
import com.example.currencyconverterapp.core.data.repository.CurrenciesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CurrenciesModule {

    @Binds
    @Singleton
    abstract fun bindCurrenciesRepository(
        currenciesRepositoryImpl: CurrenciesRepositoryImpl,
    ): CurrenciesRepository

    companion object {
        private const val CURRENCIES_DATA_STORE = "currencies_data_store"

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
}
