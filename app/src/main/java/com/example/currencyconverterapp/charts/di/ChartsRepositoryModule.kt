package com.example.currencyconverterapp.charts.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.currencyconverterapp.charts.data.local.ChartsCachedDataSerializer
import com.example.currencyconverterapp.charts.data.model.ChartsCachedData
import com.example.currencyconverterapp.charts.data.repository.ChartsCachedDataRepository
import com.example.currencyconverterapp.charts.data.repository.ChartsCachedDataRepositoryImpl
import com.example.currencyconverterapp.charts.data.repository.HistoricalExchangeRatesRepository
import com.example.currencyconverterapp.charts.data.repository.HistoricalExchangeRatesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ChartsRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHistoricalExchangeRatesRepository(
        historicalExchangeRatesRepositoryImpl: HistoricalExchangeRatesRepositoryImpl,
    ): HistoricalExchangeRatesRepository

    @Binds
    @Singleton
    abstract fun bindChartsCachedDataRepository(
        chartsCachedDataRepositoryImpl: ChartsCachedDataRepositoryImpl
    ): ChartsCachedDataRepository

    companion object {
        private const val CHARTS_DATA_STORE = "charts_data_store"

        @Provides
        @Singleton
        fun provideChartsCachedDataStore(@ApplicationContext appContext: Context): DataStore<ChartsCachedData> {
            return DataStoreFactory.create(
                serializer = ChartsCachedDataSerializer,
            ) {
                appContext.dataStoreFile(CHARTS_DATA_STORE)
            }
        }
    }

}