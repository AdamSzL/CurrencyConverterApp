package com.example.currencyconverterapp.data

import androidx.datastore.core.DataStore
import com.example.currencyconverterapp.model.ChartsCachedData
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.DateTimeExchangeRatesInfo
import com.example.currencyconverterapp.model.TimePeriod
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ChartsCachedDataRepository {

    val savedChartsData: Flow<ChartsCachedData>

    suspend fun updateSavedBaseCurrency(currency: Currency)

    suspend fun updateSavedTargetCurrency(currency: Currency)

    suspend fun updateSavedIsColumnChartEnabled(isColumnChartEnabled: Boolean)

    suspend fun updateSavedSelectedTimePeriod(timePeriod: TimePeriod)

    suspend fun updateSavedHistoricalExchangeRates(newHistoricalExchangeRates: List<DateTimeExchangeRatesInfo>)
}

class ChartsCachedDataRepositoryImpl @Inject constructor(
    private val chartsCachedDataStore: DataStore<ChartsCachedData>
): ChartsCachedDataRepository {

    override val savedChartsData: Flow<ChartsCachedData>
        get() = chartsCachedDataStore.data

    override suspend fun updateSavedBaseCurrency(currency: Currency) {
        chartsCachedDataStore.updateData {
            it.copy(
                baseCurrency = currency
            )
        }
    }

    override suspend fun updateSavedTargetCurrency(currency: Currency) {
        chartsCachedDataStore.updateData {
            it.copy(
                targetCurrency = currency
            )
        }
    }

    override suspend fun updateSavedIsColumnChartEnabled(isColumnChartEnabled: Boolean) {
        chartsCachedDataStore.updateData {
            it.copy(
                isColumnChartEnabled = isColumnChartEnabled
            )
        }
    }

    override suspend fun updateSavedSelectedTimePeriod(timePeriod: TimePeriod) {
        chartsCachedDataStore.updateData {
            it.copy(
                selectedTimePeriod = timePeriod
            )
        }
    }

    override suspend fun updateSavedHistoricalExchangeRates(newHistoricalExchangeRates: List<DateTimeExchangeRatesInfo>) {
        chartsCachedDataStore.updateData {
            it.copy(
                historicalExchangeRates = newHistoricalExchangeRates
            )
        }
    }


}