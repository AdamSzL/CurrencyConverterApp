package com.example.currencyconverterapp.charts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.charts.data.model.ChartType
import com.example.currencyconverterapp.charts.data.model.DateTimeExchangeRatesInfo
import com.example.currencyconverterapp.charts.data.model.RecentTimePeriod
import com.example.currencyconverterapp.charts.data.model.TimePeriodType
import com.example.currencyconverterapp.charts.data.repository.ChartsCachedDataRepository
import com.example.currencyconverterapp.charts.data.repository.HistoricalExchangeRatesRepository
import com.example.currencyconverterapp.charts.presentation.util.DateTimeUtils.getCurrentDate
import com.example.currencyconverterapp.charts.presentation.util.DateTimeUtils.subtractTimePeriodFromDate
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.data.util.defaultHistoricalExchangeRates
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.diff.ExtraStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ChartsViewModel @Inject constructor(
    private val historicalExchangeRatesRepository: HistoricalExchangeRatesRepository,
    private val chartsCachedDataRepository: ChartsCachedDataRepository,
): ViewModel() {
    private val _chartsUiState = MutableStateFlow(ChartsUiState())
    val chartsUiState: StateFlow<ChartsUiState> = _chartsUiState

    val axisExtraKey: ExtraStore.Key<List<String>> = ExtraStore.Key()
    val chartEntryModelProducer: ChartEntryModelProducer = ChartEntryModelProducer(
        convertToChartData(defaultHistoricalExchangeRates)
    )

    init {
        viewModelScope.launch {
            chartsCachedDataRepository.savedChartsData.first { savedChartsData ->
                val isChartDataEmpty = savedChartsData.historicalExchangeRates.isEmpty()
                _chartsUiState.update {
                    it.copy(
                        selectedBaseCurrency = savedChartsData.baseCurrency,
                        selectedTargetCurrency = savedChartsData.targetCurrency,
                        selectedChartType = savedChartsData.chartType,
                        selectedTimePeriodType = savedChartsData.selectedTimePeriodType,
                        historicalExchangeRatesUiState =
                            if (isChartDataEmpty) {
                                HistoricalExchangeRatesUiState.Loading
                            }
                            else {
                                HistoricalExchangeRatesUiState.Success
                            },
                    )
                }
                if (isChartDataEmpty) {
                    getHistoricalExchangeRates(shouldShowError = savedChartsData.historicalExchangeRates.isEmpty())
                } else {
                    updateChartEntries(savedChartsData.historicalExchangeRates)
                }
                true
            }
        }
    }

    fun errorMessageDisplayed() {
        _chartsUiState.update {
            it.copy(shouldShowErrorMessage = false)
        }
    }

    private fun updateChartEntries(entries: List<DateTimeExchangeRatesInfo>) {
        viewModelScope.launch {
            chartEntryModelProducer.setEntriesSuspending(
                convertToChartData(entries),
                updateExtras = { extraStore ->
                    extraStore.set(axisExtraKey, getDatesFromData(entries))
                }
            )
        }
    }

    fun getHistoricalExchangeRates(shouldShowError: Boolean = true) {
        val currentDate = getCurrentDate()
        with(chartsUiState.value) {
            viewModelScope.launch {
                val historicalExchangeRatesUiState = try {
                    val dateTimeStart = if (selectedTimePeriodType is TimePeriodType.Recent) {
                        subtractTimePeriodFromDate(currentDate, selectedTimePeriodType.recentTimePeriod)
                    } else {
                        (selectedTimePeriodType as TimePeriodType.Range).start
                    }
                    val dateTimeEnd = if (selectedTimePeriodType is TimePeriodType.Range) {
                        selectedTimePeriodType.end
                    } else {
                        currentDate
                    }
                    val response = historicalExchangeRatesRepository.getHistoricalExchangeRates(
                        dateTimeStart = dateTimeStart,
                        dateTimeEnd = dateTimeEnd,
                        baseCurrency = selectedBaseCurrency.code,
                        currencies = selectedTargetCurrency.code,
                        accuracy = if (
                            selectedTimePeriodType is TimePeriodType.Recent && selectedTimePeriodType.recentTimePeriod == RecentTimePeriod.ONE_DAY
                        ) {
                            "hour"
                        } else {
                            "day"
                        }
                    )
                    updateChartEntries(response.data)
                    updateSavedChartData(
                        response.data,
                        selectedBaseCurrency,
                        selectedTargetCurrency,
                        selectedTimePeriodType
                    )
                    HistoricalExchangeRatesUiState.Success
                } catch (e: Exception) {
                    restoreToLoadingState()
                    delay(200)
                    if (shouldShowError) HistoricalExchangeRatesUiState.Error else HistoricalExchangeRatesUiState.ErrorButCached
                }
                _chartsUiState.update {
                    it.copy(
                        historicalExchangeRatesUiState = historicalExchangeRatesUiState,
                        shouldShowErrorMessage = true,
                    )
                }
            }
        }
    }

    private fun updateSavedChartType(chartType: ChartType) {
        viewModelScope.launch {
            chartsCachedDataRepository.updateSavedChartType(chartType)
        }
    }

    private fun updateSavedChartData(
        historicalExchangeRates: List<DateTimeExchangeRatesInfo>,
        baseCurrency: Currency,
        targetCurrency: Currency,
        selectedTimePeriodType: TimePeriodType,
    ) {
        viewModelScope.launch {
            chartsCachedDataRepository.updateSavedChartData(
                historicalExchangeRates,
                baseCurrency,
                targetCurrency,
                selectedTimePeriodType
            )
        }
    }

    fun restoreToLoadingState() {
        _chartsUiState.update {
            it.copy(
                historicalExchangeRatesUiState = HistoricalExchangeRatesUiState.Loading,
            )
        }
    }

    fun selectBaseCurrency(currency: Currency) {
        _chartsUiState.update {
            it.copy(
                selectedBaseCurrency = currency,
            )
        }
        getHistoricalExchangeRates()
    }

    fun selectTargetCurrency(currency: Currency) {
        _chartsUiState.update {
            it.copy(
                selectedTargetCurrency = currency,
            )
        }
        getHistoricalExchangeRates()
    }

    fun updateChartType(chartType: ChartType) {
        _chartsUiState.update {
            it.copy(
                selectedChartType = chartType,
            )
        }
        updateSavedChartType(chartType)
    }

    fun updateTimePeriodType(timePeriodType: TimePeriodType) {
        _chartsUiState.update {
            it.copy(
                selectedTimePeriodType = timePeriodType
            )
        }
        getHistoricalExchangeRates()
    }

    fun swapBaseAndTargetCurrencies() {
        _chartsUiState.update {
            it.copy(
                selectedBaseCurrency = it.selectedTargetCurrency,
                selectedTargetCurrency = it.selectedBaseCurrency,
            )
        }
        getHistoricalExchangeRates()
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