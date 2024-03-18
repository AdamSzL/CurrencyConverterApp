package com.example.currencyconverterapp.core.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.currencyconverterapp.converter.presentation.ConverterScreen
import com.example.currencyconverterapp.converter.presentation.ConverterScreenWrapper
import com.example.currencyconverterapp.converter.presentation.ConverterViewModel
import com.example.currencyconverterapp.converter.presentation.util.constructConverterScreenActions
import com.example.currencyconverterapp.core.presentation.CurrenciesUiState
import com.example.currencyconverterapp.core.presentation.components.ScreenAdaptiveNavigationWrapper
import com.example.currencyconverterapp.core.presentation.util.AdaptiveContentTypes
import com.example.currencyconverterapp.core.presentation.util.CurrencyConverterNavigationType
import com.example.currencyconverterapp.watchlist.presentation.item.WatchlistItemScreen
import com.example.currencyconverterapp.watchlist.presentation.item.WatchlistItemViewModel
import com.example.currencyconverterapp.watchlist.presentation.list.WatchlistScreen
import com.example.currencyconverterapp.watchlist.presentation.list.WatchlistViewModel
import com.example.currencyconverterapp.watchlist.presentation.util.constructWatchlistItemProps
import kotlinx.coroutines.launch

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
            ConverterScreenWrapper(
                converterUiState = converterUiState,
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
                ScreenAdaptiveNavigationWrapper(
                    screenAdaptiveNavigationWrapperProps = screenAdaptiveNavigationWrapperProps,
                    fabAction = {
                        navController.navigate(WatchlistSubScreen.WatchlistAddItem.name)
                    }
                ) {
                    WatchlistScreen(
                        watchlistItems = watchlistItems,
                        watchlistEntrySize = watchlistEntrySize,
                        fabType = fabType,
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
                ScreenAdaptiveNavigationWrapper(
                    screenAdaptiveNavigationWrapperProps = screenAdaptiveNavigationWrapperProps
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
                ScreenAdaptiveNavigationWrapper(
                    screenAdaptiveNavigationWrapperProps = screenAdaptiveNavigationWrapperProps
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

class ScreenAdaptiveNavigationWrapperProps(
    val navigationType: CurrencyConverterNavigationType,
    val currentCurrencyConverterScreen: CurrencyConverterScreen,
    val navigateTo: (String) -> Unit,
    val dataHandlerUiState: String,
    val onRetryAction: () -> Unit,
)