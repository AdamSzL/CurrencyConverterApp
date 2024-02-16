package com.example.currencyconverterapp.data

import androidx.datastore.core.DataStore
import com.example.currencyconverterapp.model.CurrenciesCachedData
import com.example.currencyconverterapp.model.Currency
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CurrenciesCachedDataRepository {

    val savedCurrencies: Flow<CurrenciesCachedData>

    suspend fun updateSavedCurrencies(newCurrencies: List<Currency>)
}

class CurrenciesCachedDataRepositoryImpl @Inject constructor(
    private val currenciesCachedDataStore: DataStore<CurrenciesCachedData>
): CurrenciesCachedDataRepository {
    override val savedCurrencies: Flow<CurrenciesCachedData>
        get() = currenciesCachedDataStore.data

    override suspend fun updateSavedCurrencies(newCurrencies: List<Currency>) {
        currenciesCachedDataStore.updateData {
            it.copy(
                currencies = newCurrencies,
            )
        }
    }

}