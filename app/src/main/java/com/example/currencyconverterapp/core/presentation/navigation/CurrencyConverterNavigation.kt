package com.example.currencyconverterapp.core.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.charts.presentation.ChartsScreen
import com.example.currencyconverterapp.charts.presentation.ChartsViewModel
import com.example.currencyconverterapp.converter.presentation.ConverterScreen
import com.example.currencyconverterapp.converter.presentation.ConverterViewModel
import com.example.currencyconverterapp.core.presentation.CurrenciesUiState
import com.example.currencyconverterapp.core.presentation.components.DataStateHandler
import com.example.currencyconverterapp.watchlist.data.model.WatchlistItem
import com.example.currencyconverterapp.watchlist.presentation.item.WatchlistItemProps
import com.example.currencyconverterapp.watchlist.presentation.item.WatchlistItemScreen
import com.example.currencyconverterapp.watchlist.presentation.item.WatchlistItemUiState
import com.example.currencyconverterapp.watchlist.presentation.item.WatchlistItemViewModel
import com.example.currencyconverterapp.watchlist.presentation.list.WatchlistScreen
import com.example.currencyconverterapp.watchlist.presentation.list.WatchlistViewModel

@Composable
fun CurrencyConverterNavigation(
    navController: NavHostController,
    currenciesUiState: CurrenciesUiState,
    onCurrenciesRefresh: () -> Unit,
    onLaunchAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = CurrencyConverterScreen.Converter.name,
        modifier = modifier
    ) {
        composable(route = CurrencyConverterScreen.Converter.name) {
            val converterViewModel: ConverterViewModel = hiltViewModel()
            val converterUiState = converterViewModel.converterUiState.collectAsStateWithLifecycle().value
            DataStateHandler(
                uiState = currenciesUiState.toString(),
                errorMessage = R.string.error_loading_currency_data,
                onErrorRetryAction = onCurrenciesRefresh,
            ) {
                ConverterScreen(
                    converterUiState = converterUiState,
                    availableCurrencies = (currenciesUiState as CurrenciesUiState.Success).currencies,
                    onExchangeRatesRefresh = {
                        converterViewModel.restoreToSuccessState()
                        converterViewModel.refreshLatestExchangeRatesAndHandleError(
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
            val chartsViewModel: ChartsViewModel = hiltViewModel()
            val chartsUiState = chartsViewModel.chartsUiState.collectAsStateWithLifecycle().value
            DataStateHandler(
                uiState = currenciesUiState.toString(),
                errorMessage = R.string.error_loading_currency_data,
                onErrorRetryAction = onCurrenciesRefresh,
            ) {
                ChartsScreen(
                    chartsUiState = chartsUiState,
                    chartEntryModelProducer = chartsViewModel.chartEntryModelProducer,
                    axisExtraKey = chartsViewModel.axisExtraKey,
                    currencies = (currenciesUiState as CurrenciesUiState.Success).currencies,
                    onBaseCurrencySelection = chartsViewModel::selectBaseCurrency,
                    onTargetCurrencySelection = chartsViewModel::selectTargetCurrency,
                    onRecentTimePeriodSelection = chartsViewModel::selectRecentTimePeriod,
                    onColumnChartToggle = chartsViewModel::toggleColumnChart,
                    onChartUpdate = chartsViewModel::getHistoricalExchangeRates,
                    onLoadingStateRestore = chartsViewModel::restoreToLoadingState,
                    onBaseAndTargetCurrenciesSwap = chartsViewModel::swapBaseAndTargetCurrencies,
                    onErrorMessageDisplayed = chartsViewModel::errorMessageDisplayed,
                )
            }
        }
        navigation(
            startDestination = WatchlistSubScreen.WatchlistItems.name,
            route = CurrencyConverterScreen.Watchlist.name,
        ) {
            composable(route = WatchlistSubScreen.WatchlistItems.name) {
                val watchlistViewModel: WatchlistViewModel = hiltViewModel()
                val watchlistItems = watchlistViewModel.watchlistItems.collectAsStateWithLifecycle().value
                DataStateHandler(
                    uiState = currenciesUiState.toString(),
                    errorMessage = R.string.error_loading_currency_data,
                    onErrorRetryAction = onCurrenciesRefresh,
                ) {
                    WatchlistScreen(
                        watchlistItems = watchlistItems,
                        onWatchlistItemClicked = { watchlistItemId ->
                            navController.navigate("${WatchlistSubScreen.WatchlistEditItem.name}/${watchlistItemId}")
                        },
                        onWatchlistItemDeletion = watchlistViewModel::removeWatchlistItem,
                        onAddButtonClicked = {
                            navController.navigate(WatchlistSubScreen.WatchlistAddItem.name)
                        },
                    )
                }
            }
            composable(route = WatchlistSubScreen.WatchlistAddItem.name) {
                val watchlistItemViewModel: WatchlistItemViewModel = hiltViewModel()
                val watchlistItemUiState
                        = watchlistItemViewModel.watchlistItemUiState.collectAsStateWithLifecycle().value
                DataStateHandler(
                    uiState = currenciesUiState.toString(),
                    errorMessage = R.string.error_loading_currency_data,
                    onErrorRetryAction = onCurrenciesRefresh,
                ) {
                    WatchlistItemScreen(
                        currencies = (currenciesUiState as CurrenciesUiState.Success).currencies,
                        watchlistItemProps = constructWatchlistItemProps(
                            watchlistItemViewModel = watchlistItemViewModel,
                            watchlistItemUiState = watchlistItemUiState,
                            confirmButtonText = R.string.add,
                            onConfirmButtonClicked = { watchlistItem ->
                                watchlistItemViewModel.addWatchlistItem(watchlistItem)
                                navController.navigateUp()
                            },
                            onCancelButtonClicked = { navController.navigateUp() },
                            onLaunchAppSettingsClick = onLaunchAppSettingsClick,
                        )
                    )
                }
            }
            composable(
                route = "${WatchlistSubScreen.WatchlistEditItem.name}/{watchlist_item_id}",
                arguments = listOf(
                    navArgument("watchlist_item_id") {
                        type = NavType.StringType
                    }
                )
            ) {
                val watchlistItemViewModel: WatchlistItemViewModel = hiltViewModel()
                val watchlistItemUiState
                        = watchlistItemViewModel.watchlistItemUiState.collectAsStateWithLifecycle().value
                DataStateHandler(
                    uiState = currenciesUiState.toString(),
                    errorMessage = R.string.error_loading_currency_data,
                    onErrorRetryAction = onCurrenciesRefresh,
                ) {
                    WatchlistItemScreen(
                        currencies = (currenciesUiState as CurrenciesUiState.Success).currencies,
                        watchlistItemProps = constructWatchlistItemProps(
                            watchlistItemViewModel = watchlistItemViewModel,
                            watchlistItemUiState = watchlistItemUiState,
                            confirmButtonText = R.string.update,
                            onConfirmButtonClicked = { watchlistItem ->
                                watchlistItemViewModel.editWatchlistItem(watchlistItem)
                                navController.navigateUp()
                            },
                            onCancelButtonClicked = { navController.navigateUp() },
                            onLaunchAppSettingsClick = onLaunchAppSettingsClick,
                        )
                    )
                }
            }
        }
    }
}

private fun constructWatchlistItemProps(
    watchlistItemViewModel: WatchlistItemViewModel,
    watchlistItemUiState: WatchlistItemUiState,
    @StringRes confirmButtonText: Int,
    onConfirmButtonClicked: (WatchlistItem) -> Unit,
    onCancelButtonClicked: () -> Unit,
    onLaunchAppSettingsClick: () -> Unit,
): WatchlistItemProps {
    return WatchlistItemProps(
        watchlistItemUiState = watchlistItemUiState,
        confirmButtonText = confirmButtonText,
        onBaseCurrencySelection = watchlistItemViewModel::selectBaseCurrency,
        onTargetCurrencySelection = watchlistItemViewModel::selectTargetCurrency,
        onBaseAndTargetCurrenciesSwap = watchlistItemViewModel::swapBaseAndTargetCurrencies,
        onExchangeRateRelationSelection = watchlistItemViewModel::selectExchangeRateRelation,
        onTargetValueChange = watchlistItemViewModel::changeTargetValue,
        onConfirmButtonClicked = onConfirmButtonClicked,
        onCancelButtonClicked = onCancelButtonClicked,
        onLatestExchangeRateUpdate = watchlistItemViewModel::restoreToLoadingStateAndFetchExchangeRate,
        onNotificationsPermissionRejectionStateUpdate = watchlistItemViewModel::updateNotificationsPermissionRejectionState,
        onLaunchAppSettingsClick = onLaunchAppSettingsClick,
    )
}
