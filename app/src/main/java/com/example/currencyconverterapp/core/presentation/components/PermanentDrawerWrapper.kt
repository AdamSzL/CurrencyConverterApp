package com.example.currencyconverterapp.core.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.core.presentation.navigation.CurrencyConverterPermanentNavigationDrawer
import com.example.currencyconverterapp.core.presentation.navigation.CurrencyConverterScreen
import com.example.currencyconverterapp.core.presentation.util.CurrencyConverterNavigationType
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun PermanentDrawerWrapper(
    navigationType: CurrencyConverterNavigationType,
    currentCurrencyConverterScreen: CurrencyConverterScreen,
    navigateTo: (String) -> Unit,
    fabAction: (() -> Unit)?,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    if (navigationType == CurrencyConverterNavigationType.PERMANENT_NAVIGATION_DRAWER) {
        CurrencyConverterPermanentNavigationDrawer(
            currentCurrencyConverterScreen = currentCurrencyConverterScreen,
            navigateTo = navigateTo,
            fabAction = fabAction,
            modifier = modifier
        ) {
            content()
        }
    } else {
        content()
    }
}

@Preview
@Composable
private fun PermanentDrawerWrapperPreview() {
    CurrencyConverterAppTheme {
        PermanentDrawerWrapper(
            navigationType = CurrencyConverterNavigationType.PERMANENT_NAVIGATION_DRAWER,
            currentCurrencyConverterScreen = CurrencyConverterScreen.Converter,
            navigateTo = { },
            fabAction = { }
        ) {

        }
    }
}