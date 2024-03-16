package com.example.currencyconverterapp.charts.data.repository

import androidx.datastore.core.DataStore
import com.example.currencyconverterapp.charts.data.model.ChartType
import com.example.currencyconverterapp.charts.data.model.ChartsCachedData
import com.example.currencyconverterapp.charts.data.model.DateTimeExchangeRatesInfo
import com.example.currencyconverterapp.charts.data.model.TimePeriodType
import com.example.currencyconverterapp.core.data.model.Currency
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ChartsCachedDataRepository {

    val savedChartsData: Flow<ChartsCachedData>

    suspend fun updateSavedChartType(chartType: ChartType)

    suspend fun updateSavedChartData(
        historicalExchangeRates: List<DateTimeExchangeRatesInfo>,
        baseCurrency: Currency,
        targetCurrency: Currency,
        selectedTimePeriodType: TimePeriodType,
    )
}

class ChartsCachedDataRepositoryImpl @Inject constructor(
    private val chartsCachedDataStore: DataStore<ChartsCachedData>
): ChartsCachedDataRepository {

    override val savedChartsData: Flow<ChartsCachedData>
        get() = chartsCachedDataStore.data

    override suspend fun updateSavedChartType(chartType: ChartType) {
        chartsCachedDataStore.updateData {
            it.copy(chartType = chartType)
        }
    }

    override suspend fun updateSavedChartData(
        historicalExchangeRates: List<DateTimeExchangeRatesInfo>,
        baseCurrency: Currency,
        targetCurrency: Currency,
        selectedTimePeriodType: TimePeriodType,
    ) {
        chartsCachedDataStore.updateData {
            it.copy(
                historicalExchangeRates = historicalExchangeRates,
                baseCurrency = baseCurrency,
                targetCurrency = targetCurrency,
                selectedTimePeriodType = selectedTimePeriodType,
            )
        }
    }
}