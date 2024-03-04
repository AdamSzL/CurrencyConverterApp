package com.example.currencyconverterapp.converter.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.currencyconverterapp.converter.data.local.ConverterCachedDataSerializer
import com.example.currencyconverterapp.converter.data.model.ConverterCachedData
import com.example.currencyconverterapp.converter.data.repository.ConverterRepository
import com.example.currencyconverterapp.converter.data.repository.ConverterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ConverterModule {

    @Binds
    @Singleton
    abstract fun bindConverterRepository(
        converterRepositoryImpl: ConverterRepositoryImpl,
    ): ConverterRepository

    companion object {
        private const val CONVERTER_DATA_STORE = "converter_data_store"

        @Provides
        @Singleton
        fun provideConverterCachedDataStore(@ApplicationContext appContext: Context): DataStore<ConverterCachedData> {
            return DataStoreFactory.create(
                serializer = ConverterCachedDataSerializer,
            ) {
                appContext.dataStoreFile(CONVERTER_DATA_STORE)
            }
        }
    }
}
