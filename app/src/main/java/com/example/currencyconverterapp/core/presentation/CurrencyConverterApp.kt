package com.example.currencyconverterapp.core.presentation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.currencyconverterapp.core.presentation.navigation.CurrencyConverterBottomNavigationBar
import com.example.currencyconverterapp.core.presentation.navigation.CurrencyConverterNavigation
import com.example.currencyconverterapp.core.presentation.navigation.CurrencyConverterNavigationRail
import com.example.currencyconverterapp.core.presentation.navigation.CurrencyConverterPermanentNavigationDrawer
import com.example.currencyconverterapp.core.presentation.navigation.WatchlistSubScreen
import com.example.currencyconverterapp.core.presentation.util.CurrencyConverterNavigationType
import com.example.currencyconverterapp.core.presentation.util.NavigationUtils.getCurrentConverterWatchlistScreen

@Composable
fun CurrencyConverterApp(
    windowSize: WindowWidthSizeClass,
    onLaunchAppSettingsClick: () -> Unit,
    currenciesViewModel: CurrenciesViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route
    val (currentCurrencyConverterScreen, currentWatchlistScreen) = getCurrentConverterWatchlistScreen(route)

    val currenciesUiState = currenciesViewModel.currenciesUiState.collectAsStateWithLifecycle().value

    val navigationType = when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            CurrencyConverterNavigationType.BOTTOM_NAVIGATION
        }
        WindowWidthSizeClass.Medium -> {
            CurrencyConverterNavigationType.NAVIGATION_RAIL
        }
        WindowWidthSizeClass.Expanded -> {
            CurrencyConverterNavigationType.PERMANENT_NAVIGATION_DRAWER
        }
        else -> {
            CurrencyConverterNavigationType.BOTTOM_NAVIGATION
        }
    }

    val navigateTo = { targetRoute: String ->
        navController.navigate(targetRoute)
    }

    val mainContent: @Composable () -> Unit = {
        Scaffold(
            topBar = {
                if (navigationType == CurrencyConverterNavigationType.BOTTOM_NAVIGATION) {
                    CurrencyConverterTopAppBar(
                        currentCurrencyConverterScreen = currentCurrencyConverterScreen,
                        currentWatchlistScreen = currentWatchlistScreen,
                        canNavigateBack = currentWatchlistScreen != null && route != WatchlistSubScreen.WatchlistItems.name,
                        navigateUp = { navController.navigateUp() },
                    )
                }
            },
            bottomBar = {
                AnimatedVisibility(visible = navigationType == CurrencyConverterNavigationType.BOTTOM_NAVIGATION) {
                    CurrencyConverterBottomNavigationBar(
                        currentScreen = currentCurrencyConverterScreen,
                        navigateTo = navigateTo,
                    )
                }
            }
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Row {
                    AnimatedVisibility(visible = navigationType == CurrencyConverterNavigationType.NAVIGATION_RAIL) {
                        CurrencyConverterNavigationRail(
                            currentCurrencyConverterScreen = currentCurrencyConverterScreen,
                            navigateTo = navigateTo,
                        )
                    }
                    CurrencyConverterNavigation(
                        navController = navController,
                        currenciesUiState = currenciesUiState,
                        onCurrenciesRefresh = currenciesViewModel::restoreToLoadingStateAndRefreshCurrencies,
                        onLaunchAppSettingsClick = onLaunchAppSettingsClick,
                    )
                }
            }
        }
    }

    if (navigationType == CurrencyConverterNavigationType.PERMANENT_NAVIGATION_DRAWER) {
        CurrencyConverterPermanentNavigationDrawer(
            currentCurrencyConverterScreen = currentCurrencyConverterScreen,
            navigateTo = {
                navController.navigate(it)
            }
        ) {
            mainContent()
        }
    }
    else {
        mainContent()
    }
}