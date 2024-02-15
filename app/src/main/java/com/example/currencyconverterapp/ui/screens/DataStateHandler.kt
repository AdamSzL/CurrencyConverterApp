package com.example.currencyconverterapp.ui.screens

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.example.currencyconverterapp.ui.screens.converter.CurrenciesErrorScreen
import com.example.currencyconverterapp.ui.screens.converter.LoadingScreen

@Composable
fun DataStateHandler(
    uiState: String,
    @StringRes errorMessage: Int,
    onErrorRetryAction: () -> Unit,
    content: @Composable () -> Unit,
) {
    when (uiState) {
        "Error" -> CurrenciesErrorScreen(
            errorMessage = errorMessage,
            onErrorRetryAction = onErrorRetryAction,
        )
        "Loading" -> LoadingScreen()
        else -> content()
    }
}