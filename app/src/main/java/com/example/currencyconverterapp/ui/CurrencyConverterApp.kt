package com.example.currencyconverterapp.ui

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
import com.example.currencyconverterapp.ui.screens.CurrenciesViewModel
import com.example.currencyconverterapp.ui.screens.CurrencyConverterScreen
import com.example.currencyconverterapp.ui.screens.WatchlistSubScreen
import com.example.currencyconverterapp.ui.screens.converter.CurrencyConverterTopAppBar
import com.example.currencyconverterapp.ui.screens.converter.navigation.BottomNavigationBar

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

private fun getCurrentConverterWatchlistScreen(route: String?): Pair<CurrencyConverterScreen, WatchlistSubScreen?> {
    if (route == null || !route.contains("Watchlist")) {
        return Pair(CurrencyConverterScreen.valueOf(route ?: CurrencyConverterScreen.Converter.name), null)
    }

    val watchlistSubScreen = if (route == "${WatchlistSubScreen.WatchlistEditItem}/{watchlist_item_id}") {
        WatchlistSubScreen.WatchlistEditItem
    } else {
        WatchlistSubScreen.valueOf(route)
    }
    return Pair(CurrencyConverterScreen.Watchlist, watchlistSubScreen)
}