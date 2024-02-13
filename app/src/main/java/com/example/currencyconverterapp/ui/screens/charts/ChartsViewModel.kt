package com.example.currencyconverterapp.ui.screens.charts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.data.CurrencyConverterRepository
import com.example.currencyconverterapp.data.defaultHistoricalExchangeRates
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.DateTimeExchangeRatesInfo
import com.example.currencyconverterapp.ui.screens.charts.DateTimeHandler.getCurrentDate
import com.example.currencyconverterapp.ui.screens.charts.DateTimeHandler.subtractTimePeriodFromDate
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.diff.ExtraStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ChartsViewModel @Inject constructor(
    private val currencyConverterRepository: CurrencyConverterRepository
): ViewModel() {
    private val _chartsUiState = MutableStateFlow(ChartsUiState())
    val chartsUiState: StateFlow<ChartsUiState> = _chartsUiState

    val axisExtraKey: ExtraStore.Key<List<String>> = ExtraStore.Key()
    val chartEntryModelProducer: ChartEntryModelProducer = ChartEntryModelProducer(
        convertToChartData(defaultHistoricalExchangeRates)
    )

    init {
        getHistoricalExchangeRates()
    }

    fun selectBaseCurrency(currency: Currency) {
        _chartsUiState.update {
            it.copy(
                selectedBaseCurrency = currency
            )
        }
    }

    fun selectTargetCurrency(currency: Currency) {
        _chartsUiState.update {
            it.copy(
                selectedTargetCurrency = currency
            )
        }
    }

    fun toggleColumnChart(isColumn: Boolean) {
        _chartsUiState.update {
            it.copy(
                isColumnChartEnabled = isColumn
            )
        }
    }

    fun selectTimePeriod(timePeriod: TimePeriod) {
        _chartsUiState.update {
            it.copy(
                selectedTimePeriod = timePeriod
            )
        }
    }

    fun swapBaseAndTargetCurrencies() {
        _chartsUiState.update {
            it.copy(
                selectedBaseCurrency = it.selectedTargetCurrency,
                selectedTargetCurrency = it.selectedBaseCurrency
            )
        }
    }

    fun getHistoricalExchangeRates() {
        Log.d("XXX", "fetching historical exchange rates...")
        val currentDate = getCurrentDate()
        with(chartsUiState.value) {
            viewModelScope.launch {
                try {
                    val response = currencyConverterRepository.getHistoricalExchangeRates(
                        dateTimeStart = subtractTimePeriodFromDate(currentDate, selectedTimePeriod),
                        dateTimeEnd = currentDate,
                        baseCurrency = selectedBaseCurrency.code,
                        currencies = selectedTargetCurrency.code,
                        accuracy = if (selectedTimePeriod == TimePeriod.ONE_DAY) "hour" else "day"
                    )
                    chartEntryModelProducer.setEntriesSuspending(
                        convertToChartData(response.data),
                        updateExtras = { extraStore ->
                            extraStore.set(axisExtraKey, getDatesFromData(response.data))
                        }
                    )
                } catch (e: IOException) {
                    Log.d("XXX", "Error in getHistoricalExchangeRates")
                }
            }
        }
    }

    private fun convertToChartData(data: List<DateTimeExchangeRatesInfo>): List<FloatEntry> {
        val entries = arrayListOf<FloatEntry>()
        var xPos = 0f
        for (dateTimeExchangeRateInfo in data) {
            entries.add(FloatEntry(x = xPos, y = dateTimeExchangeRateInfo.exchangeRatesData.values.first().value!!.toFloat()))
            xPos += 1f
        }
        return entries
    }

    private fun getDatesFromData(data: List<DateTimeExchangeRatesInfo>): List<String> {
        return data.map { dateTimeExchangeRatesInfo ->
            dateTimeExchangeRatesInfo.datetime
        }
    }
}
