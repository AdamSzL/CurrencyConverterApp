package com.example.currencyconverterapp.ui.screens.charts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.data.model.Currency
import com.example.currencyconverterapp.data.model.TimePeriod
import com.example.currencyconverterapp.ui.screens.DataStateHandler
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.diff.ExtraStore
import kotlinx.coroutines.launch

@Composable
fun ChartsScreen(
    chartsUiState: ChartsUiState,
    chartEntryModelProducer: ChartEntryModelProducer,
    axisExtraKey: ExtraStore.Key<List<String>>,
    currencies: List<Currency>,
    onBaseCurrencySelection: (Currency) -> Unit,
    onTargetCurrencySelection: (Currency) -> Unit,
    onTimePeriodSelection: (TimePeriod) -> Unit,
    onColumnChartToggle: (Boolean) -> Unit,
    onLoadingStateRestore: () -> Unit,
    onChartUpdate: () -> Unit,
    onBaseAndTargetCurrenciesSwap: () -> Unit,
    onErrorMessageDisplayed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarErrorMessage = stringResource(R.string.error_loading_chart_data)

    val scope = rememberCoroutineScope()

    if ((chartsUiState.historicalExchangeRatesUiState == HistoricalExchangeRatesUiState.Error ||
        chartsUiState.historicalExchangeRatesUiState == HistoricalExchangeRatesUiState.ErrorButCached) &&
        chartsUiState.shouldShowErrorMessage
    ) {
        LaunchedEffect(Unit) {
            scope.launch {
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar(snackbarErrorMessage)
                onErrorMessageDisplayed()
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(paddingValues)
        ) {

            BaseTargetCurrenciesController(
                chartsUiState = chartsUiState,
                currencies = currencies,
                onBaseCurrencySelection = onBaseCurrencySelection,
                onTargetCurrencySelection = onTargetCurrencySelection,
                onBaseAndTargetCurrenciesSwap = onBaseAndTargetCurrenciesSwap,
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Switch(
                    checked = chartsUiState.isColumnChartEnabled,
                    onCheckedChange = {
                        onColumnChartToggle(it)
                    },
                )
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.column_chart_switch_text_gap)))
                Text(
                    text = stringResource(R.string.column_chart)
                )
            }
            TimePeriodDropdownMenu(
                chartsUiState = chartsUiState,
                onTimePeriodSelection = onTimePeriodSelection,
            )
            Spacer(modifier = modifier.height(dimensionResource(R.dimen.chart_currency_gap)))
            DataStateHandler(
                uiState = chartsUiState.historicalExchangeRatesUiState.toString(),
                errorMessage = R.string.error_loading_chart_data,
                onErrorRetryAction = {
                    onLoadingStateRestore()
                    onChartUpdate()
                }
            ) {
                HistoricalExchangeRatesChart(
                    baseCurrency = chartsUiState.selectedBaseCurrency,
                    targetCurrency = chartsUiState.selectedTargetCurrency,
                    selectedTimePeriod = chartsUiState.selectedTimePeriod,
                    isColumnChartEnabled = chartsUiState.isColumnChartEnabled,
                    chartEntryModelProducer = chartEntryModelProducer,
                    axisExtraKey = axisExtraKey,
                )
            }
        }
    }
}

fun filterChartCurrencies(currencies: List<Currency>, chartsUiState: ChartsUiState): List<Currency> {
    return currencies.filter { currency ->
        currency.code != chartsUiState.selectedBaseCurrency.code &&
        currency.code != chartsUiState.selectedTargetCurrency.code
    }
}