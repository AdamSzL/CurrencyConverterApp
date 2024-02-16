package com.example.currencyconverterapp.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.ui.screens.charts.TimePeriod
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

interface ChartsUserPreferencesRepository {

    val savedBaseCurrency: Flow<String>

    val savedTargetCurrency: Flow<String>

    val savedColumnChartsEnabled: Flow<Boolean>

    val savedSelectedTimePeriod: Flow<String>

    suspend fun updateSavedBaseCurrency(baseCurrency: Currency)

    suspend fun updateSavedTargetCurrency(targetCurrency: Currency)

    suspend fun updateSavedColumnChartsEnabled(columnChartsEnabled: Boolean)

    suspend fun updateSavedSelectedTimePeriod(selectedTimePeriod: TimePeriod)
}

class ChartsUserPreferencesRepositoryImpl @Inject constructor(
    private val currencyConverterDataStorePreferences: DataStore<Preferences>
): ChartsUserPreferencesRepository {

    private companion object {
        val BASE_CURRENCY_KEY = stringPreferencesKey("charts_base_currency")
        val TARGET_CURRENCY_KEY = stringPreferencesKey("charts_target_currency")
        val COLUMN_CHARTS_ENABLED = booleanPreferencesKey("column_charts_enabled")
        val SELECTED_TIME_PERIOD = stringPreferencesKey("selected_time_period")
    }

    override val savedBaseCurrency: Flow<String>
        = currencyConverterDataStorePreferences.getValue(
            BASE_CURRENCY_KEY, Json.encodeToString(
                defaultBaseCurrency)
        )

    override val savedTargetCurrency: Flow<String>
        = currencyConverterDataStorePreferences.getValue(
            TARGET_CURRENCY_KEY, Json.encodeToString(
                defaultTargetCurrency
            )
        )

    override val savedColumnChartsEnabled: Flow<Boolean>
        = currencyConverterDataStorePreferences.getValue(COLUMN_CHARTS_ENABLED, false)

    override val savedSelectedTimePeriod: Flow<String>
        = currencyConverterDataStorePreferences.getValue(
            SELECTED_TIME_PERIOD, TimePeriod.ONE_MONTH.label
        )

    override suspend fun updateSavedBaseCurrency(baseCurrency: Currency)
        = currencyConverterDataStorePreferences.setValue(
            BASE_CURRENCY_KEY, Json.encodeToString(baseCurrency)
        )

    override suspend fun updateSavedTargetCurrency(targetCurrency: Currency)
        = currencyConverterDataStorePreferences.setValue(
            TARGET_CURRENCY_KEY, Json.encodeToString(targetCurrency)
        )

    override suspend fun updateSavedColumnChartsEnabled(columnChartsEnabled: Boolean)
        = currencyConverterDataStorePreferences.setValue(COLUMN_CHARTS_ENABLED, columnChartsEnabled)

    override suspend fun updateSavedSelectedTimePeriod(selectedTimePeriod: TimePeriod)
        = currencyConverterDataStorePreferences.setValue(SELECTED_TIME_PERIOD, selectedTimePeriod.label)
}