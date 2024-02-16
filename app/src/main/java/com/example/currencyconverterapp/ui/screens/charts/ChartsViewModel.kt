package com.example.currencyconverterapp.ui.screens.charts

//import com.example.currencyconverterapp.data.ChartsCachedDataRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.data.ChartsCachedDataRepository
import com.example.currencyconverterapp.data.CurrencyConverterRepository
import com.example.currencyconverterapp.data.defaultHistoricalExchangeRates
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.DateTimeExchangeRatesInfo
import com.example.currencyconverterapp.model.TimePeriod
import com.example.currencyconverterapp.ui.screens.charts.DateTimeHandler.getCurrentDate
import com.example.currencyconverterapp.ui.screens.charts.DateTimeHandler.subtractTimePeriodFromDate
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.diff.ExtraStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ChartsViewModel @Inject constructor(
    private val currencyConverterRepository: CurrencyConverterRepository,
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
                _chartsUiState.update {
                    it.copy(
                        selectedBaseCurrency = savedChartsData.baseCurrency,
                        selectedTargetCurrency = savedChartsData.targetCurrency,
                        isColumnChartEnabled = savedChartsData.isColumnChartEnabled,
                        selectedTimePeriod = savedChartsData.selectedTimePeriod,
                        historicalExchangeRatesUiState = HistoricalExchangeRatesUiState.Success,
                    )
                }
                updateChartEntries(savedChartsData.historicalExchangeRates)
//                getHistoricalExchangeRates()
                true
            }
        }
    }

    fun updateChartEntries(entries: List<DateTimeExchangeRatesInfo>) {
        viewModelScope.launch {
            chartEntryModelProducer.setEntriesSuspending(
                convertToChartData(entries),
                updateExtras = { extraStore ->
                    extraStore.set(axisExtraKey, getDatesFromData(entries))
                }
            )
        }
    }

    fun getHistoricalExchangeRates() {
        val currentDate = getCurrentDate()
        with(chartsUiState.value) {
            viewModelScope.launch {
                val historicalExchangeRatesUiState = try {
                    val response = currencyConverterRepository.getHistoricalExchangeRates(
                        dateTimeStart = subtractTimePeriodFromDate(currentDate, selectedTimePeriod),
                        dateTimeEnd = currentDate,
                        baseCurrency = selectedBaseCurrency.code,
                        currencies = selectedTargetCurrency.code,
                        accuracy = if (selectedTimePeriod == TimePeriod.ONE_DAY) "hour" else "day"
                    )
                    updateChartEntries(response.data)
                    chartsCachedDataRepository.updateSavedHistoricalExchangeRates(response.data)
                    HistoricalExchangeRatesUiState.Success
                } catch (e: IOException) {
                    HistoricalExchangeRatesUiState.Error
                }
                _chartsUiState.update {
                    it.copy(
                        historicalExchangeRatesUiState = historicalExchangeRatesUiState
                    )
                }
            }
        }
    }

    private fun updateSavedBaseCurrency(baseCurrency: Currency) {
        viewModelScope.launch {
            chartsCachedDataRepository.updateSavedBaseCurrency(baseCurrency)
        }
    }

    private fun updateSavedTargetCurrency(targetCurrency: Currency) {
        viewModelScope.launch {
            chartsCachedDataRepository.updateSavedTargetCurrency(targetCurrency)
        }
    }

    private fun updateSavedColumnChartsEnabled(columnChartsEnabled: Boolean) {
        viewModelScope.launch {
            chartsCachedDataRepository.updateSavedIsColumnChartEnabled(columnChartsEnabled)
        }
    }

    private fun updateSavedSelectedTimePeriod(selectedTimePeriod: TimePeriod) {
        viewModelScope.launch {
            chartsCachedDataRepository.updateSavedSelectedTimePeriod(selectedTimePeriod)
        }
    }

    fun selectBaseCurrency(currency: Currency) {
        _chartsUiState.update {
            it.copy(
                selectedBaseCurrency = currency
            )
        }
        updateSavedBaseCurrency(currency)
    }

    fun selectTargetCurrency(currency: Currency) {
        _chartsUiState.update {
            it.copy(
                selectedTargetCurrency = currency
            )
        }
        updateSavedTargetCurrency(currency)
    }

    fun toggleColumnChart(isColumn: Boolean) {
        _chartsUiState.update {
            it.copy(
                isColumnChartEnabled = isColumn
            )
        }
        updateSavedColumnChartsEnabled(isColumn)
    }

    fun selectTimePeriod(timePeriod: TimePeriod) {
        _chartsUiState.update {
            it.copy(
                selectedTimePeriod = timePeriod
            )
        }
        updateSavedSelectedTimePeriod(timePeriod)
    }

    fun swapBaseAndTargetCurrencies() {
        _chartsUiState.update {
            updateSavedBaseCurrency(it.selectedTargetCurrency)
            updateSavedTargetCurrency(it.selectedBaseCurrency)
            it.copy(
                selectedBaseCurrency = it.selectedTargetCurrency,
                selectedTargetCurrency = it.selectedBaseCurrency
            )
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
