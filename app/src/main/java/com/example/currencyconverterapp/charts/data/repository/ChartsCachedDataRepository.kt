package com.example.currencyconverterapp.charts.data.repository

import androidx.datastore.core.DataStore
import com.example.currencyconverterapp.charts.data.model.ChartsCachedData
import com.example.currencyconverterapp.charts.data.model.DateTimeExchangeRatesInfo
import com.example.currencyconverterapp.charts.data.model.RecentTimePeriod
import com.example.currencyconverterapp.core.data.model.Currency
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ChartsCachedDataRepository {

    val savedChartsData: Flow<ChartsCachedData>

    suspend fun updateSavedIsColumnChartEnabled(isColumnChartEnabled: Boolean)

    suspend fun updateSavedChartData(
        historicalExchangeRates: List<DateTimeExchangeRatesInfo>,
        baseCurrency: Currency,
        targetCurrency: Currency,
        selectedRecentTimePeriod: RecentTimePeriod,
    )
}

class ChartsCachedDataRepositoryImpl @Inject constructor(
    private val chartsCachedDataStore: DataStore<ChartsCachedData>
): ChartsCachedDataRepository {

    override val savedChartsData: Flow<ChartsCachedData>
        get() = chartsCachedDataStore.data

    override suspend fun updateSavedIsColumnChartEnabled(isColumnChartEnabled: Boolean) {
        chartsCachedDataStore.updateData {
            it.copy(isColumnChartEnabled = isColumnChartEnabled)
        }
    }

    override suspend fun updateSavedChartData(
        historicalExchangeRates: List<DateTimeExchangeRatesInfo>,
        baseCurrency: Currency,
        targetCurrency: Currency,
        selectedRecentTimePeriod: RecentTimePeriod
    ) {
        chartsCachedDataStore.updateData {
            it.copy(
                historicalExchangeRates = historicalExchangeRates,
                baseCurrency = baseCurrency,
                targetCurrency = targetCurrency,
                selectedRecentTimePeriod = selectedRecentTimePeriod
            )
        }
    }
}