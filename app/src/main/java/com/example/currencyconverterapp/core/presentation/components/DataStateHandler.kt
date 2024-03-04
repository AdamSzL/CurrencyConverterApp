package com.example.currencyconverterapp.core.presentation.components

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable

@Composable
fun DataStateHandler(
    uiState: String,
    @StringRes errorMessage: Int,
    onErrorRetryAction: () -> Unit,
    content: @Composable () -> Unit,
) {
    when (uiState) {
        "Error" -> ErrorScreen(
            errorMessage = errorMessage,
            onErrorRetryAction = onErrorRetryAction,
        )
        "Loading" -> LoadingScreen()
        else -> content()
    }
}