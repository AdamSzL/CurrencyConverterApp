package com.example.currencyconverterapp.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.core.data.util.defaultAvailableCurrencies
import com.example.currencyconverterapp.core.presentation.CurrenciesUiState
import com.example.currencyconverterapp.core.presentation.navigation.CurrencyConverterNavigationRail
import com.example.currencyconverterapp.core.presentation.navigation.CurrencyConverterScreen
import com.example.currencyconverterapp.core.presentation.navigation.ScreenAdaptiveNavigationWrapperProps
import com.example.currencyconverterapp.core.presentation.util.CurrencyConverterNavigationType
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun ScreenAdaptiveNavigationWrapper(
    screenAdaptiveNavigationWrapperProps: ScreenAdaptiveNavigationWrapperProps,
    modifier: Modifier = Modifier,
    fabAction: (() -> Unit)? = null,
    screenContent: @Composable () -> Unit,
) {
    with (screenAdaptiveNavigationWrapperProps) {
        PermanentDrawerWrapper(
            navigationType = navigationType,
            currentCurrencyConverterScreen = currentCurrencyConverterScreen,
            navigateTo = navigateTo,
            fabAction = fabAction,
            modifier = modifier
        ) {
            Row {
                AnimatedVisibility(visible = navigationType == CurrencyConverterNavigationType.NAVIGATION_RAIL) {
                    CurrencyConverterNavigationRail(
                        currentCurrencyConverterScreen = currentCurrencyConverterScreen,
                        navigateTo = navigateTo,
                        fabAction = fabAction,
                    )
                }
                DataStateHandler(
                    uiState = dataHandlerUiState,
                    errorMessage = R.string.error_loading_currency_data,
                    onErrorRetryAction = onRetryAction,
                ) {
                    screenContent()
                }
            }
        }
    }
}

@Preview
@Composable
private fun ScreenAdaptiveNavigationWrapperPreview() {
    CurrencyConverterAppTheme {
        ScreenAdaptiveNavigationWrapper(
            screenAdaptiveNavigationWrapperProps = ScreenAdaptiveNavigationWrapperProps(
                navigationType = CurrencyConverterNavigationType.PERMANENT_NAVIGATION_DRAWER,
                currentCurrencyConverterScreen = CurrencyConverterScreen.Converter,
                navigateTo = { },
                dataHandlerUiState = CurrenciesUiState.Success(defaultAvailableCurrencies).toString(),
                onRetryAction = { },
            ),
            fabAction = { }
        ) {

        }
    }
}