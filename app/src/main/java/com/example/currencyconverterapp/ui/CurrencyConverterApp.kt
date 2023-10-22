package com.example.currencyconverterapp.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.ui.screens.charts.ChartsScreen
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
    converterViewModel: ConverterViewModel = viewModel(factory = ConverterViewModel.Factory)
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CurrencyConverterScreen.valueOf(
        backStackEntry?.destination?.route ?: CurrencyConverterScreen.Converter.name
    )

    val converterUiState = converterViewModel.converterUiState.collectAsState().value

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
                startDestination = CurrencyConverterScreen.Converter.name,
                modifier = Modifier
            ) {

                composable(route = CurrencyConverterScreen.Converter.name) {
                    ConverterScreen(
                        converterUiState = converterUiState,
                        onBaseCurrencySelection = converterViewModel::selectBaseCurrency,
                        onBaseCurrencyValueChange = converterViewModel::setBaseCurrencyValue,
                        onTargetCurrencySelection = converterViewModel::selectTargetCurrency,
                        onTargetCurrencyAddition = converterViewModel::addTargetCurrency,
                        onSelectionModeToggle = converterViewModel::toggleSelectionMode,
                        onConversionEntryToggle = converterViewModel::toggleConversionEntrySelection,
                    )
                }

                composable(route = CurrencyConverterScreen.Charts.name) {
                    ChartsScreen(

                    )
                }

            }
        }
    }
}