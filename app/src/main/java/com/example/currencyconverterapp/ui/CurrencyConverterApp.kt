package com.example.currencyconverterapp.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.ui.screens.SharedViewModel
import com.example.currencyconverterapp.ui.screens.charts.ChartsScreen
import com.example.currencyconverterapp.ui.screens.charts.ChartsViewModel
import com.example.currencyconverterapp.ui.screens.converter.ConverterScreen
import com.example.currencyconverterapp.ui.screens.converter.ConverterViewModel
import com.example.currencyconverterapp.ui.screens.converter.CurrencyConverterTopAppBar
import com.example.currencyconverterapp.ui.screens.converter.navigation.BottomNavigationBar

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
    converterViewModel: ConverterViewModel = hiltViewModel(),
    chartsViewModel: ChartsViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CurrencyConverterScreen.valueOf(
        backStackEntry?.destination?.route ?: CurrencyConverterScreen.Converter.name
    )

    val converterUiState = converterViewModel.converterUiState.collectAsState().value
    val chartsUiState = chartsViewModel.chartsUiState.collectAsState().value

    Scaffold(
        topBar = {
            CurrencyConverterTopAppBar(
                currentScreen = currentScreen,
                isSelectionModeEnabled = converterUiState.isSelectionModeEnabled,
                onSelectionModeToggle = converterViewModel::toggleSelectionMode,
                onSelectedTargetCurrenciesDeletion = converterViewModel::removeSelectedConversionEntries,
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
                startDestination = "currency_converter",
                modifier = Modifier
            ) {
                navigation(
                    startDestination = CurrencyConverterScreen.Converter.name,
                    route = "currency_converter"
                ) {
                    composable(route = CurrencyConverterScreen.Converter.name) { entry ->
                        val sharedViewModel = entry.sharedViewModel<SharedViewModel>(navController,)
                        val currencies by sharedViewModel.currencies.collectAsState()
                        ConverterScreen(
                            converterUiState = converterUiState,
                            availableCurrencies = currencies,
                            onBaseCurrencySelection = converterViewModel::selectBaseCurrency,
                            onBaseCurrencyValueChange = converterViewModel::setBaseCurrencyValue,
                            onTargetCurrencySelection = converterViewModel::selectTargetCurrency,
                            onTargetCurrencyAddition = converterViewModel::addTargetCurrency,
                            onSelectionModeToggle = converterViewModel::toggleSelectionMode,
                            onConversionEntryToggle = converterViewModel::toggleConversionEntrySelection,
                            onConversionEntryDeletion = converterViewModel::deleteConversionEntry,
                        )
                    }

                    composable(route = CurrencyConverterScreen.Charts.name) { entry ->
                        val sharedViewModel = entry.sharedViewModel<SharedViewModel>(navController,)
                        val currencies by sharedViewModel.currencies.collectAsState()
                        ChartsScreen(
                            chartsUiState = chartsUiState,
                            currencies = currencies,
                            onBaseCurrencySelection = chartsViewModel::selectBaseCurrency,
                            onTargetCurrencySelection = chartsViewModel::selectTargetCurrency
                        )
                    }
                }
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}