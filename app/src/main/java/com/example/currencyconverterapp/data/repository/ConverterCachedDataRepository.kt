package com.example.currencyconverterapp.data.repository

import androidx.datastore.core.DataStore
import com.example.currencyconverterapp.data.model.ConverterCachedData
import com.example.currencyconverterapp.data.model.Currency
import com.example.currencyconverterapp.data.model.ExchangeRate
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ConverterCachedDataRepository {

    val savedConverterData: Flow<ConverterCachedData>

    suspend fun updateSavedBaseCurrency(currency: Currency)

    suspend fun updateSavedCurrencyValue(value: Double)

    suspend fun updateSavedExchangeRates(newExchangeRates: List<ExchangeRate>)
}

class ConverterCachedDataRepositoryImpl @Inject constructor(
    private val converterCachedDataStore: DataStore<ConverterCachedData>,
): ConverterCachedDataRepository {

    override val savedConverterData: Flow<ConverterCachedData>
        get() = converterCachedDataStore.data

    override suspend fun updateSavedBaseCurrency(currency: Currency) {
        converterCachedDataStore.updateData {
            it.copy(
                baseCurrency = currency
            )
        }
    }

    override suspend fun updateSavedCurrencyValue(value: Double) {
        converterCachedDataStore.updateData {
            it.copy(
                baseCurrencyValue = value
            )
        }
    }

    override suspend fun updateSavedExchangeRates(newExchangeRates: List<ExchangeRate>) {
        converterCachedDataStore.updateData {
            it.copy(
                exchangeRates = newExchangeRates,
            )
        }
    }
}