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
import androidx.navigation.compose.navigation
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
import com.example.currencyconverterapp.ui.screens.watchlist.WatchlistScreen
import com.example.currencyconverterapp.ui.screens.watchlist.WatchlistViewModel
import com.example.currencyconverterapp.ui.screens.watchlist.add.WatchlistAddItemScreen
import com.example.currencyconverterapp.ui.screens.watchlist.add.WatchlistAddItemViewModel
import com.example.currencyconverterapp.ui.screens.watchlist.edit.WatchlistEditItemScreen

enum class CurrencyConverterScreen(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    Converter(
        route = "converter",
        title = R.string.converter,
        icon = R.drawable.ic_money,
    ),
    Charts(
        route = "charts",
        title = R.string.charts,
        icon = R.drawable.ic_chart,
    ),
    Watchlist(
        route = "watchlist",
        title = R.string.watchlist,
        icon = R.drawable.ic_watchlist,
    )
}

enum class WatchlistScreen(
    val route: String,
    @StringRes val title: Int
) {
    WatchlistItems(
        route = "watchlist_list",
        title = R.string.watchlist,
    ),
    WatchlistAddItem(
        route = "watchlist_add",
        title = R.string.watchlist_add,
    ),
    WatchlistEditItem(
        route = "watchlist_edit",
        title = R.string.watchlist_edit,
    )
}

@Composable
fun CurrencyConverterApp(
    currenciesViewModel: CurrenciesViewModel = hiltViewModel(),
    converterViewModel: ConverterViewModel = hiltViewModel(),
    chartsViewModel: ChartsViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route
    val (currentCurrencyConverterScreen, currentWatchlistScreen) = if (route != null && route.contains("Watchlist")) {
        Pair(CurrencyConverterScreen.Watchlist, WatchlistScreen.valueOf(route))
    } else {
        Pair(CurrencyConverterScreen.valueOf(route ?: CurrencyConverterScreen.Converter.name), null)
    }

    val currenciesUiState = currenciesViewModel.currenciesUiState.collectAsStateWithLifecycle().value
    val converterUiState = converterViewModel.converterUiState.collectAsStateWithLifecycle().value
    val chartsUiState = chartsViewModel.chartsUiState.collectAsStateWithLifecycle().value

    val fetchDataAgain = {
        currenciesViewModel.restoreToLoadingStateAndFetchCurrencies()
        converterViewModel.fetchExchangeRates(
            converterUiState.baseCurrency,
            converterUiState.exchangeRates
        )
        chartsViewModel.getHistoricalExchangeRates()
    }

    Scaffold(
        topBar = {
            CurrencyConverterTopAppBar(
                currentCurrencyConverterScreen = currentCurrencyConverterScreen,
                currentWatchlistScreen = currentWatchlistScreen,
                canNavigateBack = currentWatchlistScreen != null && route != WatchlistScreen.WatchlistItems.name,
                navigateUp = { navController.navigateUp() },
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navigateTo = { route ->
                    navController.navigate(route)
                },
                currentScreen = currentCurrencyConverterScreen,
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
                        errorMessage = R.string.error_loading_currency_data,
                        onErrorRetryAction = fetchDataAgain,
                    ) {
                        ConverterScreen(
                            converterUiState = converterUiState,
                            availableCurrencies = (currenciesUiState as CurrenciesUiState.Success).currencies,
                            onExchangeRatesRefresh = {
                                converterViewModel.restoreToSuccessState()
                                converterViewModel.fetchExchangeRates(
                                    converterUiState.baseCurrency,
                                    converterUiState.exchangeRates
                                )
                            },
                            onBaseCurrencySelection = converterViewModel::selectBaseCurrency,
                            onBaseCurrencyValueChange = converterViewModel::setBaseCurrencyValue,
                            onTargetCurrencySelection = converterViewModel::selectTargetCurrency,
                            onTargetCurrencyAddition = converterViewModel::addTargetCurrency,
                            onConversionEntryDeletion = converterViewModel::deleteConversionEntry,
                            onConversionEntryDeletionUndo = converterViewModel::undoConversionEntryDeletion,
                            onErrorMessageDisplayed = converterViewModel::errorMessageDisplayed,
                        )
                    }
                }
                composable(route = CurrencyConverterScreen.Charts.name) {
                    DataStateHandler(
                        uiState = currenciesUiState.toString(),
                        errorMessage = R.string.error_loading_currency_data,
                        onErrorRetryAction = fetchDataAgain,
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
                            onLoadingStateRestore = chartsViewModel::restoreToLoadingState,
                            onBaseAndTargetCurrenciesSwap = chartsViewModel::swapBaseAndTargetCurrencies,
                            onErrorMessageDisplayed = chartsViewModel::errorMessageDisplayed,
                        )
                    }
                }
                navigation(
                    startDestination = WatchlistScreen.WatchlistItems.name,
                    route = CurrencyConverterScreen.Watchlist.name,
                ) {
                    composable(route = WatchlistScreen.WatchlistItems.name) {
                        val watchlistViewModel: WatchlistViewModel = hiltViewModel()
                        val watchlistItems = watchlistViewModel.watchlistItems.collectAsStateWithLifecycle().value
                        DataStateHandler(
                            uiState = currenciesUiState.toString(),
                            errorMessage = R.string.error_loading_currency_data,
                            onErrorRetryAction = fetchDataAgain
                        ) {
                            WatchlistScreen(
                                watchlistItems = watchlistItems,
                                onWatchlistItemEdition = watchlistViewModel::updateWatchlistItem,
                                onWatchlistItemDeletion = watchlistViewModel::removeWatchlistItem,
                                onAddButtonClicked = {
                                    navController.navigate(WatchlistScreen.WatchlistAddItem.name)
                                }
                            )
                        }
                    }
                    composable(route = WatchlistScreen.WatchlistAddItem.name) {
                        val watchlistAddItemViewModel: WatchlistAddItemViewModel = hiltViewModel()
                        val watchlistAddItemUiState
                            = watchlistAddItemViewModel.watchlistAddItemUiState.collectAsStateWithLifecycle().value
                        DataStateHandler(
                            uiState = currenciesUiState.toString(),
                            errorMessage = R.string.error_loading_currency_data,
                            onErrorRetryAction = fetchDataAgain
                        ) {
                            WatchlistAddItemScreen(
                                currencies = (currenciesUiState as CurrenciesUiState.Success).currencies,
                                watchlistAddItemUiState = watchlistAddItemUiState,
                                onBaseCurrencySelection = watchlistAddItemViewModel::selectBaseCurrency,
                                onTargetCurrencySelection = watchlistAddItemViewModel::selectTargetCurrency,
                                onBaseAndTargetCurrenciesSwap = watchlistAddItemViewModel::swapBaseAndTargetCurrencies,
                                onExchangeRateRelationSelection = watchlistAddItemViewModel::selectExchangeRateRelation,
                                onTargetValueChange = watchlistAddItemViewModel::changeTargetValue,
                                onWatchlistItemAddition = watchlistAddItemViewModel::addWatchlistItem,
                                onLatestExchangeRateUpdate = watchlistAddItemViewModel::restoreToLoadingStateAndFetchExchangeRate,
                                navigateUp = {
                                    navController.navigateUp()
                                }
                            )
                        }
                    }
                    composable(route = WatchlistScreen.WatchlistEditItem.name) {
                        DataStateHandler(
                            uiState = currenciesUiState.toString(),
                            errorMessage = R.string.error_loading_currency_data,
                            onErrorRetryAction = fetchDataAgain
                        ) {
                            WatchlistEditItemScreen(
                                currencies = (currenciesUiState as CurrenciesUiState.Success).currencies,
                            )
                        }
                    }
                }
            }
        }
    }
}