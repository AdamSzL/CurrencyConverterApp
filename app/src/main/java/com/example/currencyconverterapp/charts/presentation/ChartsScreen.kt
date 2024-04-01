package com.example.currencyconverterapp.charts.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.charts.data.model.TimePeriodType
import com.example.currencyconverterapp.charts.presentation.util.ChartsScreenActions
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.data.util.defaultAvailableCurrencies
import com.example.currencyconverterapp.core.presentation.components.DataStateHandler
import com.example.currencyconverterapp.core.presentation.util.ChartControllerType
import com.example.currencyconverterapp.core.presentation.util.ChartsScreenContentType
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.diff.ExtraStore
import kotlinx.coroutines.launch

@Composable
fun ChartsScreen(
    chartsUiState: ChartsUiState,
    contentType: ChartsScreenContentType,
    controllerType: ChartControllerType,
    chartEntryModelProducer: ChartEntryModelProducer,
    axisExtraKey: ExtraStore.Key<List<String>>,
    currencies: List<Currency>,
    chartsScreenActions: ChartsScreenActions,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarErrorMessage = stringResource(R.string.error_loading_chart_data)

    val scope = rememberCoroutineScope()

    var isRangeTimePeriodPickerVisible by remember {
        mutableStateOf(false)
    }

    if ((chartsUiState.historicalExchangeRatesUiState == HistoricalExchangeRatesUiState.Error ||
        chartsUiState.historicalExchangeRatesUiState == HistoricalExchangeRatesUiState.ErrorButCached) &&
        chartsUiState.shouldShowErrorMessage
    ) {
        LaunchedEffect(Unit) {
            scope.launch {
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar(snackbarErrorMessage)
                chartsScreenActions.onErrorMessageDisplayed()
            }
        }
    }

    with (chartsScreenActions) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { paddingValues ->
            AnimatedContent(
                targetState = isRangeTimePeriodPickerVisible
            ) { targetState ->
                if (targetState) {
                    RangeTimePeriodPicker(
                        onDismiss = {
                            isRangeTimePeriodPickerVisible = false
                        },
                        onSave = { startDate, endDate ->
                            onTimePeriodTypeUpdate(TimePeriodType.Range(startDate, endDate))
                        },
                    )
                } else {
                    val chartController: @Composable () -> Unit = {
                        ChartController(
                            chartsUiState = chartsUiState,
                            currencies = currencies,
                            type = controllerType,
                            onBaseCurrencySelection = onBaseCurrencySelection,
                            onTargetCurrencySelection = onTargetCurrencySelection,
                            onTimePeriodTypeUpdate = onTimePeriodTypeUpdate,
                            onChartTypeUpdate = onChartTypeUpdate,
                            onBaseAndTargetCurrenciesSwap = onBaseAndTargetCurrenciesSwap,
                            onRangeTimePeriodPickerLaunch = {
                                isRangeTimePeriodPickerVisible = true
                            },
                            modifier = Modifier.then(
                                if (contentType == ChartsScreenContentType.TABS) {
                                    Modifier.fillMaxHeight()
                                } else {
                                    Modifier
                                }
                            )
                        )
                    }
                    val chartContent: @Composable () -> Unit = {
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
                                selectedTimePeriodType = chartsUiState.selectedTimePeriodType,
                                chartType = chartsUiState.selectedChartType,
                                chartsScreenContentType = contentType,
                                chartEntryModelProducer = chartEntryModelProducer,
                                axisExtraKey = axisExtraKey,
                            )
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier
                            .padding(paddingValues)
                    ) {
                        if (contentType == ChartsScreenContentType.FULL) {
                            chartController()
                            chartContent()
                        } else {
                            ChartsScreenTabLayout(
                                chartController = chartController,
                                chartContent = chartContent
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ChartsScreenLoadingStatePreview() {
    CurrencyConverterAppTheme {
        ChartsScreen(
            chartsUiState = ChartsUiState(),
            contentType = ChartsScreenContentType.FULL,
            controllerType = ChartControllerType.VERTICAL,
            chartEntryModelProducer = ChartEntryModelProducer(),
            axisExtraKey = ExtraStore.Key<List<String>>(),
            currencies = defaultAvailableCurrencies,
            chartsScreenActions = ChartsScreenActions(
                onBaseCurrencySelection = { },
                onTargetCurrencySelection = { },
                onTimePeriodTypeUpdate = { },
                onChartTypeUpdate = { },
                onLoadingStateRestore = { },
                onChartUpdate = { },
                onBaseAndTargetCurrenciesSwap = { },
                onErrorMessageDisplayed = {  }
            ),
        )
    }
}

@Preview
@Composable
private fun ChartsScreenErrorStatePreview() {
    CurrencyConverterAppTheme {
        ChartsScreen(
            chartsUiState = ChartsUiState().copy(historicalExchangeRatesUiState = HistoricalExchangeRatesUiState.Error),
            contentType = ChartsScreenContentType.FULL,
            controllerType = ChartControllerType.VERTICAL,
            chartEntryModelProducer = ChartEntryModelProducer(),
            axisExtraKey = ExtraStore.Key<List<String>>(),
            currencies = defaultAvailableCurrencies,
            chartsScreenActions = ChartsScreenActions(
                onBaseCurrencySelection = { },
                onTargetCurrencySelection = { },
                onTimePeriodTypeUpdate = { },
                onChartTypeUpdate = { },
                onLoadingStateRestore = { },
                onChartUpdate = { },
                onBaseAndTargetCurrenciesSwap = { },
                onErrorMessageDisplayed = {  }
            ),
        )
    }
}

@Preview
@Composable
private fun ChartsScreenLoadingStateTabsPreview() {
    CurrencyConverterAppTheme {
        ChartsScreen(
            chartsUiState = ChartsUiState().copy(historicalExchangeRatesUiState = HistoricalExchangeRatesUiState.Error),
            contentType = ChartsScreenContentType.TABS,
            controllerType = ChartControllerType.VERTICAL,
            chartEntryModelProducer = ChartEntryModelProducer(),
            axisExtraKey = ExtraStore.Key<List<String>>(),
            currencies = defaultAvailableCurrencies,
            chartsScreenActions = ChartsScreenActions(
                onBaseCurrencySelection = { },
                onTargetCurrencySelection = { },
                onTimePeriodTypeUpdate = { },
                onChartTypeUpdate = { },
                onLoadingStateRestore = { },
                onChartUpdate = { },
                onBaseAndTargetCurrenciesSwap = { },
                onErrorMessageDisplayed = {  }
            ),
        )
    }
}
