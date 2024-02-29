package com.example.currencyconverterapp.data.repository

import androidx.datastore.core.DataStore
import com.example.currencyconverterapp.data.model.ConverterCachedData
import com.example.currencyconverterapp.data.model.ExchangeRate
import com.example.currencyconverterapp.data.network.CurrencyConverterApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface ConverterRepository {

    fun getLatestExchangeRatesStream(): Flow<List<ExchangeRate>>

}

class ConverterRepositoryImpl @Inject constructor(
    private val currencyConverterApiService: CurrencyConverterApiService,
    private val converterDataStore: DataStore<ConverterCachedData>
): ConverterRepository {

    override fun getLatestExchangeRatesStream(): Flow<List<ExchangeRate>>
        = converterDataStore.data.map {
            it.exchangeRates
        }
}