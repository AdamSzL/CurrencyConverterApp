package com.example.currencyconverterapp.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.ui.screens.CurrenciesViewModel
import com.example.currencyconverterapp.ui.screens.DataStateHandler
import com.example.currencyconverterapp.ui.screens.charts.ChartsScreen
import com.example.currencyconverterapp.ui.screens.charts.ChartsViewModel
import com.example.currencyconverterapp.ui.screens.converter.ConverterScreen
import com.example.currencyconverterapp.ui.screens.converter.ConverterViewModel
import com.example.currencyconverterapp.ui.screens.converter.CurrenciesUiState
import com.example.currencyconverterapp.ui.screens.converter.CurrencyConverterTopAppBar
import com.example.currencyconverterapp.ui.screens.converter.navigation.BottomNavigationBar
import com.example.currencyconverterapp.ui.screens.loading.LoadingScreenType

enum class CurrencyConverterScreen(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    Converter(
        route = "converter",
        title = R.string.currency_converter,
        icon = R.drawable.ic_money,
    ),
    Charts(
        route = "charts",
        title = R.string.currency_charts,
        icon = R.drawable.ic_chart,
    ),
}

@Composable
fun CurrencyConverterApp(
    currenciesViewModel: CurrenciesViewModel = hiltViewModel(),
    converterViewModel: ConverterViewModel = hiltViewModel(),
    chartsViewModel: ChartsViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CurrencyConverterScreen.valueOf(
        backStackEntry?.destination?.route ?: CurrencyConverterScreen.Converter.name
    )

    val currenciesUiState = currenciesViewModel.currenciesUiState.collectAsStateWithLifecycle().value
    val converterUiState = converterViewModel.converterUiState.collectAsStateWithLifecycle().value
    val chartsUiState = chartsViewModel.chartsUiState.collectAsStateWithLifecycle().value

    val updateConverterAndChartsData = { sharedFetch: () -> Unit ->
        sharedFetch()
        converterViewModel.fetchExchangeRates(
            baseCurrency = converterUiState.baseCurrency,
        )
        chartsViewModel.getHistoricalExchangeRates()
    }

    Scaffold(
        topBar = {
            CurrencyConverterTopAppBar(
                currentScreen = currentScreen,
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navigateTo = { route ->
                    navController.navigate(route)
                },
                currentScreen = currentScreen,
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(
                navController = navController,
                startDestination = CurrencyConverterScreen.Converter.name,
                modifier = Modifier
            ) {
                composable(route = CurrencyConverterScreen.Converter.name) {
                    DataStateHandler(
                        uiState = currenciesUiState.toString(),
                        loadingScreenType = LoadingScreenType.FULL_CONVERTER,
                        errorMessage = R.string.error_loading_currency_data,
                        onErrorRetryAction = {
                            currenciesViewModel.restoreToLoadingState()
                            updateConverterAndChartsData(currenciesViewModel::fetchCurrencies)
                        }
                    ) {
                        ConverterScreen(
                            converterUiState = converterUiState,
                            availableCurrencies = (currenciesUiState as CurrenciesUiState.Success).currencies,
                            onExchangeRatesUpdate = converterViewModel::fetchExchangeRates,
                            onBaseCurrencySelection = converterViewModel::selectBaseCurrency,
                            onBaseCurrencyValueChange = converterViewModel::setBaseCurrencyValue,
                            onTargetCurrencySelection = converterViewModel::selectTargetCurrency,
                            onTargetCurrencyAddition = converterViewModel::addTargetCurrency,
                            onConversionEntryDeletion = converterViewModel::deleteConversionEntry,
                            onConversionEntryDeletionUndo = converterViewModel::undoConversionEntryDeletion,
                        )
                    }
                }
                composable(route = CurrencyConverterScreen.Charts.name) {
                    DataStateHandler(
                        uiState = currenciesUiState.toString(),
                        loadingScreenType = LoadingScreenType.FULL_CHARTS,
                        errorMessage = R.string.error_loading_currency_data,
                        onErrorRetryAction = {
                            currenciesViewModel.restoreToLoadingState()
                            updateConverterAndChartsData(currenciesViewModel::fetchCurrencies)
                        }
                    ) {
                        ChartsScreen(
                            chartsUiState = chartsUiState,
                            chartEntryModelProducer = chartsViewModel.chartEntryModelProducer,
                            axisExtraKey = chartsViewModel.axisExtraKey,
                            currencies = (currenciesUiState as CurrenciesUiState.Success).currencies,
                            onBaseCurrencySelection = chartsViewModel::selectBaseCurrency,
                            onTargetCurrencySelection = chartsViewModel::selectTargetCurrency,
                            onTimePeriodSelection = chartsViewModel::selectTimePeriod,
                            onColumnChartToggle = chartsViewModel::toggleColumnChart,
                            onChartUpdate = chartsViewModel::getHistoricalExchangeRates,
                            onBaseAndTargetCurrenciesSwap = chartsViewModel::swapBaseAndTargetCurrencies,
                        )
                    }
                }
            }
        }
    }
}