package com.example.currencyconverterapp.converter.data.repository

import androidx.datastore.core.DataStore
import com.example.currencyconverterapp.converter.data.model.ConverterCachedData
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.data.model.ExchangeRate
import com.example.currencyconverterapp.core.data.network.CurrencyConverterApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ConverterRepository {
    fun getLatestConverterDataStream(): Flow<ConverterCachedData>

    suspend fun refreshLatestExchangeRates(
        baseCurrency: Currency,
        exchangeRates: List<ExchangeRate>,
    )

    suspend fun updateExchangeRates(exchangeRates: List<ExchangeRate>)

    suspend fun updateBaseCurrency(baseCurrency: Currency)

    suspend fun updateCurrencyValue(value: Double)
}

class ConverterRepositoryImpl @Inject constructor(
    private val currencyConverterApiService: CurrencyConverterApiService,
    private val converterDataStore: DataStore<ConverterCachedData>
): ConverterRepository {

    override fun getLatestConverterDataStream(): Flow<ConverterCachedData>
        = converterDataStore.data

    override suspend fun refreshLatestExchangeRates(
        baseCurrency: Currency,
        exchangeRates: List<ExchangeRate>,
    ) {
        val latestExchangeRates = currencyConverterApiService.getLatestExchangeRates(
            baseCurrency.code,
            exchangeRates.joinToString(",") { it.code }
        )
        converterDataStore.updateData {
            it.copy(
                baseCurrency = baseCurrency,
                exchangeRates = latestExchangeRates.data.values.toList()
            )
        }
    }

    override suspend fun updateExchangeRates(exchangeRates: List<ExchangeRate>) {
        converterDataStore.updateData {
            it.copy(
                exchangeRates = exchangeRates
            )
        }
    }

    override suspend fun updateBaseCurrency(baseCurrency: Currency) {
        converterDataStore.updateData {
            it.copy(
                baseCurrency = baseCurrency
            )
        }
    }

    override suspend fun updateCurrencyValue(value: Double) {
        converterDataStore.updateData {
            it.copy(
                baseCurrencyValue = value
            )
        }
    }
}