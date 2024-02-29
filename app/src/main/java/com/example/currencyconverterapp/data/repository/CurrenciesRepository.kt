package com.example.currencyconverterapp.data.repository

import androidx.datastore.core.DataStore
import com.example.currencyconverterapp.data.model.CurrenciesCachedData
import com.example.currencyconverterapp.data.model.Currency
import com.example.currencyconverterapp.data.network.CurrencyConverterApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface CurrenciesRepository {
    fun getCurrenciesStream(): Flow<List<Currency>>

    suspend fun refreshCurrencies()
}

class CurrenciesRepositoryImpl @Inject constructor(
    private val currencyConverterApiService: CurrencyConverterApiService,
    private val currenciesDataStore: DataStore<CurrenciesCachedData>
): CurrenciesRepository {

    override fun getCurrenciesStream(): Flow<List<Currency>>
        = currenciesDataStore.data.map {
            it.currencies
        }

    override suspend fun refreshCurrencies() {
        val currenciesApiResponse = currencyConverterApiService.getCurrencies()
        currenciesDataStore.updateData {
            it.copy(
                currencies = currenciesApiResponse.data.values.toList()
            )
        }
    }
}
