package com.example.currencyconverterapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.currencyconverterapp.charts.presentation.ChartsScreen
import com.example.currencyconverterapp.charts.presentation.ChartsViewModel
import com.example.currencyconverterapp.charts.presentation.util.constructChartsScreenActions
import com.example.currencyconverterapp.core.presentation.CurrenciesUiState
import com.example.currencyconverterapp.core.presentation.components.ScreenAdaptiveNavigationWrapper
import com.example.currencyconverterapp.core.presentation.util.ChartControllerType
import com.example.currencyconverterapp.core.presentation.util.ChartsScreenContentType

@Composable
fun ChartsRoute(
    screenAdaptiveNavigationWrapperProps: ScreenAdaptiveNavigationWrapperProps,
    chartsScreenContentType: ChartsScreenContentType,
    chartControllerType: ChartControllerType,
    currenciesUiState: CurrenciesUiState,
) {
    val chartsViewModel: ChartsViewModel = hiltViewModel()
    val chartsUiState = chartsViewModel.chartsUiState.collectAsStateWithLifecycle().value
    ScreenAdaptiveNavigationWrapper(
        screenAdaptiveNavigationWrapperProps = screenAdaptiveNavigationWrapperProps,
    ) {
        ChartsScreen(
            chartsUiState = chartsUiState,
            contentType = chartsScreenContentType,
            controllerType = chartControllerType,
            chartEntryModelProducer = chartsViewModel.chartEntryModelProducer,
            axisExtraKey = chartsViewModel.axisExtraKey,
            currencies = (currenciesUiState as CurrenciesUiState.Success).currencies,
            chartsScreenActions = constructChartsScreenActions(chartsViewModel)
        )
    }
}