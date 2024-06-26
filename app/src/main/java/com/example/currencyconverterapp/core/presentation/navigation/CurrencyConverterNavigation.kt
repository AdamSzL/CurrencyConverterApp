package com.example.currencyconverterapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.currencyconverterapp.core.presentation.CurrenciesUiState
import com.example.currencyconverterapp.core.presentation.util.AdaptiveContentTypes
import com.example.currencyconverterapp.core.presentation.util.CurrencyConverterNavigationType

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
    val screenAdaptiveNavigationWrapperProps = ScreenAdaptiveNavigationWrapperProps(
        navigationType = adaptiveContentTypes.navigationType,
        currentCurrencyConverterScreen = currentCurrencyConverterScreen,
        navigateTo = navigateTo,
        dataHandlerUiState = currenciesUiState.toString(),
        onRetryAction = onCurrenciesRefresh
    )

    with (adaptiveContentTypes) {
        NavHost(
            navController = navController,
            startDestination = CurrencyConverterScreen.Converter.name,
            modifier = modifier
        ) {
            composable(route = CurrencyConverterScreen.Converter.name) {
                ConverterRoute(
                    currenciesUiState = currenciesUiState,
                    fabType = fabType,
                    screenAdaptiveNavigationWrapperProps = screenAdaptiveNavigationWrapperProps,
                    conversionResultsListItemSize = conversionResultsListItemSize,
                    converterAddCurrencyContainerType = converterAddCurrencyContainerType,
                )
            }
            composable(route = CurrencyConverterScreen.Charts.name) {
                ChartsRoute(
                    screenAdaptiveNavigationWrapperProps = screenAdaptiveNavigationWrapperProps,
                    chartsScreenContentType = chartsScreenContentType,
                    chartControllerType = chartsControllerType,
                    currenciesUiState = currenciesUiState
                )
            }
            navigation(
                startDestination = WatchlistSubScreen.WatchlistItems.name,
                route = CurrencyConverterScreen.Watchlist.name,
            ) {
                composable(route = WatchlistSubScreen.WatchlistItems.name) {
                    WatchlistItemsRoute(
                        watchlistScreenContentType = watchlistScreenContentType,
                        currenciesUiState = currenciesUiState,
                        fabType = fabType,
                        watchlistEntrySize = watchlistEntrySize,
                        watchlistItemScreenContentType = watchlistItemScreenContentType,
                        screenAdaptiveNavigationWrapperProps = screenAdaptiveNavigationWrapperProps,
                        onLaunchAppSettingsClick = onLaunchAppSettingsClick,
                        navigateTo = { route ->
                            navController.navigate(route)
                        }
                    )
                }
                composable(route = WatchlistSubScreen.WatchlistAddItem.name) {
                    WatchlistAddItemRoute(
                        screenAdaptiveNavigationWrapperProps = screenAdaptiveNavigationWrapperProps,
                        currenciesUiState = currenciesUiState,
                        watchlistItemScreenContentType = watchlistItemScreenContentType,
                        onLaunchAppSettingsClick = onLaunchAppSettingsClick,
                        navigateUp = {
                            navController.navigateUp()
                        }
                    )
                }
                composable(
                    route = "${WatchlistSubScreen.WatchlistEditItem.name}/{watchlist_item_id}",
                    arguments = listOf(
                        navArgument("watchlist_item_id") {
                            type = NavType.StringType
                        }
                    )
                ) {
                    WatchlistEditItemRoute(
                        screenAdaptiveNavigationWrapperProps = screenAdaptiveNavigationWrapperProps,
                        watchlistItemScreenContentType = watchlistItemScreenContentType,
                        currenciesUiState = currenciesUiState,
                        onLaunchAppSettingsClick = onLaunchAppSettingsClick,
                        navigateUp = {
                            navController.navigateUp()
                        }
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