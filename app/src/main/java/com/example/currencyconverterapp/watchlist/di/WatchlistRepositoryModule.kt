package com.example.currencyconverterapp.watchlist.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.work.WorkManager
import com.example.currencyconverterapp.watchlist.data.local.WatchlistDataSerializer
import com.example.currencyconverterapp.watchlist.data.model.WatchlistData
import com.example.currencyconverterapp.watchlist.data.repository.LatestExchangeRatesRepository
import com.example.currencyconverterapp.watchlist.data.repository.LatestExchangeRatesRepositoryImpl
import com.example.currencyconverterapp.watchlist.data.repository.WatchlistDataRepository
import com.example.currencyconverterapp.watchlist.data.repository.WatchlistDataRepositoryImpl
import com.example.currencyconverterapp.watchlist.data.repository.WatchlistWorkManagerRepository
import com.example.currencyconverterapp.watchlist.data.repository.WatchlistWorkManagerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WatchlistRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLatestExchangeRatesRepository(
        latestExchangeRatesRepositoryImpl: LatestExchangeRatesRepositoryImpl,
    ): LatestExchangeRatesRepository

    @Binds
    @Singleton
    abstract fun bindWatchlistDataRepository(
        watchlistDataRepositoryImpl: WatchlistDataRepositoryImpl
    ): WatchlistDataRepository

    @Binds
    @Singleton
    abstract fun bindWatchlistWorkManagerRepository(
        watchlistWorkManagerRepositoryImpl: WatchlistWorkManagerRepositoryImpl
    ): WatchlistWorkManagerRepository

    companion object {
        const val WATCHLIST_DATA_STORE = "watchlist_data_store"

        @Provides
        @Singleton
        fun provideWatchlistDataStore(@ApplicationContext appContext: Context): DataStore<WatchlistData> {
            return DataStoreFactory.create(
                serializer = WatchlistDataSerializer,
            ) {
                appContext.dataStoreFile(WATCHLIST_DATA_STORE)
            }
        }

        @Provides
        @Singleton
        fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
            return WorkManager.getInstance(context)
        }
    }
}