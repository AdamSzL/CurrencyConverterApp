package com.example.currencyconverterapp.ui.screens

import androidx.compose.runtime.Composable
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.ui.screens.converter.CurrenciesErrorScreen
import com.example.currencyconverterapp.ui.screens.converter.CurrenciesUiState
import com.example.currencyconverterapp.ui.screens.loading.LoadingScreen
import com.example.currencyconverterapp.ui.screens.loading.LoadingScreenType

@Composable
fun DataStateHandler(
    currenciesUiState: CurrenciesUiState,
    loadingScreenType: LoadingScreenType,
    onErrorRetryAction: () -> Unit,
    content: @Composable () -> Unit,
) {
    when (currenciesUiState) {
        is CurrenciesUiState.Success -> content()
        is CurrenciesUiState.Error -> CurrenciesErrorScreen(
            errorMessage = R.string.error_loading_currency_data,
            onErrorRetryAction = onErrorRetryAction,
        )
        is CurrenciesUiState.Loading -> LoadingScreen(loadingScreenType)
    }
}