package com.example.currencyconverterapp.charts.presentation.util

import com.example.currencyconverterapp.charts.data.model.ChartType
import com.example.currencyconverterapp.charts.data.model.TimePeriodType
import com.example.currencyconverterapp.charts.presentation.ChartsViewModel
import com.example.currencyconverterapp.core.data.model.Currency

class ChartsScreenActions(
    val onBaseCurrencySelection: (Currency) -> Unit,
    val onTargetCurrencySelection: (Currency) -> Unit,
    val onTimePeriodTypeUpdate: (TimePeriodType) -> Unit,
    val onChartTypeUpdate: (ChartType) -> Unit,
    val onChartUpdate: () -> Unit,
    val onLoadingStateRestore: () -> Unit,
    val onBaseAndTargetCurrenciesSwap: () -> Unit,
    val onErrorMessageDisplayed: () -> Unit,
)

fun constructChartsScreenActions(
    chartsViewModel: ChartsViewModel
): ChartsScreenActions {
    return ChartsScreenActions(
        onBaseCurrencySelection = chartsViewModel::selectBaseCurrency,
        onTargetCurrencySelection = chartsViewModel::selectTargetCurrency,
        onTimePeriodTypeUpdate = chartsViewModel::updateTimePeriodType,
        onChartTypeUpdate = chartsViewModel::updateChartType,
        onChartUpdate = chartsViewModel::getHistoricalExchangeRates,
        onLoadingStateRestore = chartsViewModel::restoreToLoadingState,
        onBaseAndTargetCurrenciesSwap = chartsViewModel::swapBaseAndTargetCurrencies,
        onErrorMessageDisplayed = chartsViewModel::errorMessageDisplayed,
    )
}
