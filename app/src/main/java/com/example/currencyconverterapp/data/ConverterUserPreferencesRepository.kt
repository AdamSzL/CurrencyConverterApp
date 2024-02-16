package com.example.currencyconverterapp.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.currencyconverterapp.model.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import javax.inject.Inject

interface ConverterUserPreferencesRepository {

    val savedCurrencies: Flow<String>

    val savedBaseCurrency: Flow<String>

    val savedCurrencyValue: Flow<Double>

    suspend fun updateSavedCurrencies(currencies: String)

    suspend fun updateSavedBaseCurrency(currency: Currency)

    suspend fun updateSavedCurrencyValue(value: Double)
}

class ConverterUserPreferencesRepositoryImpl @Inject constructor(
    private val currencyConverterDataStorePreferences: DataStore<Preferences>,
): ConverterUserPreferencesRepository {

    private companion object {
        val CURRENCIES_KEY = stringPreferencesKey("currencies")
        val BASE_CURRENCY_KEY = stringPreferencesKey("base_currency")
        val CURRENCY_VALUE_KEY = doublePreferencesKey("currency_value")
    }

    override val savedCurrencies: Flow<String>
        = currencyConverterDataStorePreferences.getValue(CURRENCIES_KEY, "PLN,USD")

    override val savedBaseCurrency: Flow<String>
        = currencyConverterDataStorePreferences.getValue(
            BASE_CURRENCY_KEY, Json.encodeToString(
                defaultBaseCurrency)
        )

    override val savedCurrencyValue: Flow<Double>
        = currencyConverterDataStorePreferences.getValue(CURRENCY_VALUE_KEY, 1.00)

    override suspend fun updateSavedCurrencies(currencies: String)
        = currencyConverterDataStorePreferences.setValue(CURRENCIES_KEY, currencies)

    override suspend fun updateSavedBaseCurrency(currency: Currency)
        = currencyConverterDataStorePreferences.setValue(BASE_CURRENCY_KEY, Json.encodeToString(currency))

    override suspend fun updateSavedCurrencyValue(value: Double)
        = currencyConverterDataStorePreferences.setValue(CURRENCY_VALUE_KEY, value)
}

fun <T>DataStore<Preferences>.getValue(valueKey: Preferences.Key<T>, defaultValue: T) = this.data
    .catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }
    .map { preferences ->
        preferences[valueKey] ?: defaultValue
    }

suspend fun <T>DataStore<Preferences>.setValue(valueKey: Preferences.Key<T>, value: T) {
    this.edit { preferences ->
        preferences[valueKey] = value
    }
}