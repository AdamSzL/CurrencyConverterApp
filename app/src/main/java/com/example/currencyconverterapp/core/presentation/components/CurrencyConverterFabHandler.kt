package com.example.currencyconverterapp.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.currencyconverterapp.converter.presentation.ConverterFloatingActionButton
import com.example.currencyconverterapp.core.presentation.navigation.CurrencyConverterScreen
import com.example.currencyconverterapp.core.presentation.util.FabSize
import com.example.currencyconverterapp.watchlist.presentation.list.WatchlistFloatingActionButton

@Composable
fun CurrencyConverterFabHandler(
    currentCurrencyConverterScreen: CurrencyConverterScreen,
    size: FabSize,
    fabAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = currentCurrencyConverterScreen == CurrencyConverterScreen.Converter,
        modifier = modifier
    ) {
        ConverterFloatingActionButton(
            size = size,
            onClick = fabAction,
        )
    }
    AnimatedVisibility(
        visible = currentCurrencyConverterScreen == CurrencyConverterScreen.Watchlist,
        modifier = modifier
    ) {
        WatchlistFloatingActionButton(
            size = size,
            onClick = fabAction,
        )
    }
}