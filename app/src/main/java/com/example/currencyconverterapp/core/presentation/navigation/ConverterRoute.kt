package com.example.currencyconverterapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.currencyconverterapp.converter.presentation.ConverterScreenWrapper
import com.example.currencyconverterapp.converter.presentation.ConverterViewModel
import com.example.currencyconverterapp.converter.presentation.util.constructConverterScreenActions
import com.example.currencyconverterapp.core.presentation.CurrenciesUiState
import com.example.currencyconverterapp.core.presentation.util.ConversionResultsListItemSize
import com.example.currencyconverterapp.core.presentation.util.ConverterAddCurrencyContainerType
import com.example.currencyconverterapp.core.presentation.util.FloatingActionButtonType

@Composable
fun ConverterRoute(
    currenciesUiState: CurrenciesUiState,
    fabType: FloatingActionButtonType,
    screenAdaptiveNavigationWrapperProps: ScreenAdaptiveNavigationWrapperProps,
    conversionResultsListItemSize: ConversionResultsListItemSize,
    converterAddCurrencyContainerType: ConverterAddCurrencyContainerType,
) {
    val converterViewModel: ConverterViewModel = hiltViewModel()
    val converterUiState = converterViewModel.converterUiState.collectAsStateWithLifecycle().value
    val converterBaseCurrencyValue = converterViewModel.currencyValue
    ConverterScreenWrapper(
        converterUiState = converterUiState,
        converterBaseCurrencyValue = converterBaseCurrencyValue,
        currenciesUiState = currenciesUiState,
        fabType = fabType,
        screenAdaptiveNavigationWrapperProps = screenAdaptiveNavigationWrapperProps,
        conversionResultsListItemSize = conversionResultsListItemSize,
        converterAddCurrencyContainerType = converterAddCurrencyContainerType,
        converterScreenActions = constructConverterScreenActions(
            converterViewModel = converterViewModel,
            onExchangeRatesRefresh = {
                converterViewModel.restoreToSuccessState()
                converterViewModel.refreshLatestExchangeRatesAndHandleError(
                    converterUiState.baseCurrency,
                    converterUiState.exchangeRates
                )
            }
        )
    )
}