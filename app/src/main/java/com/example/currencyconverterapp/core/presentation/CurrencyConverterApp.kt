package com.example.currencyconverterapp.core.presentation

import androidx.compose.animation.AnimatedVisibility
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
import com.example.currencyconverterapp.core.presentation.navigation.WatchlistSubScreen
import com.example.currencyconverterapp.core.presentation.util.CurrencyConverterNavigationType
import com.example.currencyconverterapp.core.presentation.util.FloatingActionButtonType
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

    val fabType = when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            FloatingActionButtonType.BOTTOM_RIGHT
        }
        else -> {
            FloatingActionButtonType.TOP_LEFT
        }
    }

    val navigateTo = { targetRoute: String ->
        navController.navigate(targetRoute)
    }

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
            CurrencyConverterNavigation(
                navController = navController,
                navigationType = navigationType,
                currentCurrencyConverterScreen = currentCurrencyConverterScreen,
                currenciesUiState = currenciesUiState,
                fabType = fabType,
                onCurrenciesRefresh = currenciesViewModel::restoreToLoadingStateAndRefreshCurrencies,
                onLaunchAppSettingsClick = onLaunchAppSettingsClick,
                navigateTo = navigateTo,
            )
        }
    }
}