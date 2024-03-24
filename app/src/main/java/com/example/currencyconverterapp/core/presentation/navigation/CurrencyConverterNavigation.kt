package com.example.currencyconverterapp.core.presentation.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.currencyconverterapp.charts.presentation.util.constructChartsScreenActions
import com.example.currencyconverterapp.converter.presentation.ConverterScreenWrapper
import com.example.currencyconverterapp.converter.presentation.ConverterViewModel
import com.example.currencyconverterapp.converter.presentation.util.constructConverterScreenActions
import com.example.currencyconverterapp.core.presentation.CurrenciesUiState
import com.example.currencyconverterapp.core.presentation.components.ScreenAdaptiveNavigationWrapper
import com.example.currencyconverterapp.core.presentation.util.AdaptiveContentTypes
import com.example.currencyconverterapp.core.presentation.util.CurrencyConverterNavigationType
import com.example.currencyconverterapp.core.presentation.util.WatchlistScreenContentType
import com.example.currencyconverterapp.watchlist.presentation.item.WatchlistItemScreen
import com.example.currencyconverterapp.watchlist.presentation.item.WatchlistItemViewModel
import com.example.currencyconverterapp.watchlist.presentation.list.WatchlistScreenWrapper
import com.example.currencyconverterapp.watchlist.presentation.list.WatchlistViewModel
import com.example.currencyconverterapp.watchlist.presentation.util.constructWatchlistItemProps

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterNavigation(
    navController: NavHostController,
    adaptiveContentTypes: AdaptiveContentTypes,
    currentCurrencyConverterScreen: CurrencyConverterScreen,
    currenciesUiState: CurrenciesUiState,
    onCurrenciesRefresh: () -> Unit,
    onLaunchAppSettingsClick: () -> Unit,
    navigateTo: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val (
        navigationType,
        fabType,
        _,
        chartControllerType,
        chartsScreenContentType,
        conversionResultsListItemSize,
        watchlistEntrySize,
        converterAddCurrencyContainerType,
        watchlistScreenContentType,
    ) = adaptiveContentTypes

    val screenAdaptiveNavigationWrapperProps = ScreenAdaptiveNavigationWrapperProps(
        navigationType = navigationType,
        currentCurrencyConverterScreen = currentCurrencyConverterScreen,
        navigateTo = navigateTo,
        dataHandlerUiState = currenciesUiState.toString(),
        onRetryAction = onCurrenciesRefresh
    )

    NavHost(
        navController = navController,
        startDestination = CurrencyConverterScreen.Converter.name,
        modifier = modifier
    ) {
        composable(route = CurrencyConverterScreen.Converter.name) {
            val converterViewModel: ConverterViewModel = hiltViewModel()
            val converterUiState = converterViewModel.converterUiState.collectAsStateWithLifecycle().value
            val converterBaseCurrencyValue = converterViewModel.currencyValue
            ConverterScreenWrapper(
                converterUiState = converterUiState,
                converterBaseCurrencyValue = converterBaseCurrencyValue,
                currenciesUiState = currenciesUiState,
                fabType = fabType,
                screenAdaptiveNavigationWrapperProps = screenAdaptiveNavigationWrapperProps,
                conversionResultsListItemSize = conversionResultsListItemSize,
                converterAddCurrencyContainerType = converterAddCurrencyContainerType,
                converterScreenActions = constructConverterScreenActions(
                    converterViewModel = converterViewModel,
                    onExchangeRatesRefresh = {
                        converterViewModel.restoreToSuccessState()
                        converterViewModel.refreshLatestExchangeRatesAndHandleError(
                            converterUiState.baseCurrency,
                            converterUiState.exchangeRates
                        )
                    }
                )
            )
        }
        composable(route = CurrencyConverterScreen.Charts.name) {
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
        navigation(
            startDestination = WatchlistSubScreen.WatchlistItems.name,
            route = CurrencyConverterScreen.Watchlist.name,
        ) {
            composable(route = WatchlistSubScreen.WatchlistItems.name) {
                val watchlistViewModel: WatchlistViewModel = hiltViewModel()
                val watchlistItems = watchlistViewModel.watchlistItems.collectAsStateWithLifecycle().value
                var (currencies, watchlistItemProps, selectedItemId, onWatchlistItemUpdate, onBackToAdditionReset) = WatchlistAdaptiveProps()
                if (watchlistScreenContentType == WatchlistScreenContentType.TWO_PANELS) {
                    val watchlistItemViewModel: WatchlistItemViewModel = hiltViewModel()
                    val watchlistItemUiState
                            = watchlistItemViewModel.watchlistItemUiState.collectAsStateWithLifecycle().value
                    selectedItemId = watchlistItemUiState.itemId
                    currencies = (currenciesUiState as CurrenciesUiState.Success).currencies
                    onWatchlistItemUpdate = watchlistItemViewModel::updateSelectedItem
                    onBackToAdditionReset = watchlistItemViewModel::resetBackToAddition
                    watchlistItemProps = constructWatchlistItemProps(
                        watchlistItemViewModel = watchlistItemViewModel,
                        watchlistItemTargetValue = watchlistItemViewModel.targetValue,
                        watchlistItemUiState = watchlistItemUiState,
                        confirmButtonText = if (selectedItemId != null) R.string.update else R.string.add,
                        onConfirmButtonClicked = { watchlistItem ->
                            if (selectedItemId != null) {
                                watchlistItemViewModel.editWatchlistItem(watchlistItem)
                            } else {
                                watchlistItemViewModel.addWatchlistItem(watchlistItem)
                            }
                        },
                        onCancelButtonClicked = {  },
                        onLaunchAppSettingsClick = onLaunchAppSettingsClick,
                    )
                }
                WatchlistScreenWrapper(
                    watchlistItems = watchlistItems,
                    watchlistEntrySize = watchlistEntrySize,
                    watchlistScreenContentType = watchlistScreenContentType,
                    currencies = currencies,
                    watchlistItemProps = watchlistItemProps,
                    fabType = fabType,
                    screenAdaptiveNavigationWrapperProps = screenAdaptiveNavigationWrapperProps,
                    fabAction = {
                        if (watchlistScreenContentType == WatchlistScreenContentType.ONE_PANEL) {
                            navController.navigate(WatchlistSubScreen.WatchlistAddItem.name)
                        } else {
                            onBackToAdditionReset!!()
                        }
                    },
                    onWatchlistItemClicked = { watchlistItemId ->
                        if (watchlistScreenContentType == WatchlistScreenContentType.ONE_PANEL) {
                            navController.navigate("${WatchlistSubScreen.WatchlistEditItem.name}/${watchlistItemId}")
                        } else {
                            (onWatchlistItemUpdate!!)(watchlistItemId)
                        }
                    },
                    onWatchlistItemDeletion = { watchlistItemId ->
                        if (watchlistScreenContentType == WatchlistScreenContentType.TWO_PANELS
                            && selectedItemId == watchlistItemId
                        ) {
                            onBackToAdditionReset!!()
                        }
                        watchlistViewModel.removeWatchlistItem(watchlistItemId)
                    },
                    onAddButtonClicked = {
                        navController.navigate(WatchlistSubScreen.WatchlistAddItem.name)
                    }
                )
            }
            composable(route = WatchlistSubScreen.WatchlistAddItem.name) {
                val watchlistItemViewModel: WatchlistItemViewModel = hiltViewModel()
                val watchlistItemUiState
                        = watchlistItemViewModel.watchlistItemUiState.collectAsStateWithLifecycle().value
                ScreenAdaptiveNavigationWrapper(
                    screenAdaptiveNavigationWrapperProps = screenAdaptiveNavigationWrapperProps
                ) {
                    Row {
                        WatchlistItemScreen(
                            currencies = (currenciesUiState as CurrenciesUiState.Success).currencies,
                            watchlistItemProps = constructWatchlistItemProps(
                                watchlistItemViewModel = watchlistItemViewModel,
                                watchlistItemUiState = watchlistItemUiState,
                                watchlistItemTargetValue = watchlistItemViewModel.targetValue,
                                confirmButtonText = R.string.add,
                                onConfirmButtonClicked = { watchlistItem ->
                                    watchlistItemViewModel.addWatchlistItem(watchlistItem)
                                    navController.navigateUp()
                                },
                                onCancelButtonClicked = { navController.navigateUp() },
                                onLaunchAppSettingsClick = onLaunchAppSettingsClick,
                            ),
                            shouldDisplayCancelButton = true,
                        )
                    }
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
                ScreenAdaptiveNavigationWrapper(
                    screenAdaptiveNavigationWrapperProps = screenAdaptiveNavigationWrapperProps
                ) {
                    Row {
                        WatchlistItemScreen(
                            currencies = (currenciesUiState as CurrenciesUiState.Success).currencies,
                            watchlistItemProps = constructWatchlistItemProps(
                                watchlistItemViewModel = watchlistItemViewModel,
                                watchlistItemUiState = watchlistItemUiState,
                                watchlistItemTargetValue = watchlistItemViewModel.targetValue,
                                confirmButtonText = R.string.update,
                                onConfirmButtonClicked = { watchlistItem ->
                                    watchlistItemViewModel.editWatchlistItem(watchlistItem)
                                    navController.navigateUp()
                                },
                                onCancelButtonClicked = { navController.navigateUp() },
                                onLaunchAppSettingsClick = onLaunchAppSettingsClick,
                            ),
                            shouldDisplayCancelButton = true,
                        )
                    }
                }
            }
        }
    }
}

class ScreenAdaptiveNavigationWrapperProps(
    val navigationType: CurrencyConverterNavigationType,
    val currentCurrencyConverterScreen: CurrencyConverterScreen,
    val navigateTo: (String) -> Unit,
    val dataHandlerUiState: String,
    val onRetryAction: () -> Unit,
)