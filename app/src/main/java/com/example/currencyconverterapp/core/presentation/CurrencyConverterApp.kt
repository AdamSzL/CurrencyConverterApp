package com.example.currencyconverterapp.core.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.currencyconverterapp.core.presentation.navigation.BottomNavigationBar
import com.example.currencyconverterapp.core.presentation.navigation.CurrencyConverterNavigation
import com.example.currencyconverterapp.core.presentation.navigation.WatchlistSubScreen
import com.example.currencyconverterapp.core.presentation.util.NavigationUtils.getCurrentConverterWatchlistScreen

@Composable
fun CurrencyConverterApp(
    currenciesViewModel: CurrenciesViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route
    val (currentCurrencyConverterScreen, currentWatchlistScreen) = getCurrentConverterWatchlistScreen(route)

    val currenciesUiState = currenciesViewModel.currenciesUiState.collectAsStateWithLifecycle().value

    Scaffold(
        topBar = {
            CurrencyConverterTopAppBar(
                currentCurrencyConverterScreen = currentCurrencyConverterScreen,
                currentWatchlistScreen = currentWatchlistScreen,
                canNavigateBack = currentWatchlistScreen != null && route != WatchlistSubScreen.WatchlistItems.name,
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
            CurrencyConverterNavigation(
                navController = navController,
                currenciesUiState = currenciesUiState,
                onCurrenciesRefresh = currenciesViewModel::restoreToLoadingStateAndRefreshCurrencies,
            )
        }
    }
}