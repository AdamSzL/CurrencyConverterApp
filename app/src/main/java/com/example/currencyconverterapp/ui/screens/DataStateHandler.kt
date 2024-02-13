package com.example.currencyconverterapp.ui.screens

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.example.currencyconverterapp.ui.screens.converter.CurrenciesErrorScreen
import com.example.currencyconverterapp.ui.screens.loading.LoadingScreen
import com.example.currencyconverterapp.ui.screens.loading.LoadingScreenType

@Composable
fun DataStateHandler(
    uiState: String,
    loadingScreenType: LoadingScreenType,
    @StringRes errorMessage: Int,
    onErrorRetryAction: () -> Unit,
    content: @Composable () -> Unit,
) {
    when (uiState) {
        "Error" -> CurrenciesErrorScreen(
            errorMessage = errorMessage,
            onErrorRetryAction = onErrorRetryAction,
        )
        "Loading" -> LoadingScreen(loadingScreenType)
        else -> content()
    }
}