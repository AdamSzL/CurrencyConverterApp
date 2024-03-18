package com.example.currencyconverterapp.converter.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.currencyconverterapp.converter.presentation.base_controller.BaseCurrencyController
import com.example.currencyconverterapp.converter.presentation.conversion_results.ConversionResultsList
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.presentation.components.LoadingScreen
import com.example.currencyconverterapp.core.presentation.util.ConversionResultsListItemSize

@Composable
fun ConverterScreenMainContent(
    converterUiState: ConverterUiState,
    conversionResultsListItemSize: ConversionResultsListItemSize,
    availableCurrencies: List<Currency>,
    onExchangeRatesRefresh: () -> Unit,
    onBaseCurrencySelection: (Currency) -> Unit,
    onBaseCurrencyValueChange: (Double) -> Unit,
    onConversionEntryDeletion: (String) -> Unit,
    onConversionEntryDeletionSnackbarDisplay: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        val baseCurrencyData = BaseCurrencyData(
            currencies = availableCurrencies,
            baseCurrency = converterUiState.baseCurrency,
            baseCurrencyValue = converterUiState.baseCurrencyValue
        )

        BaseCurrencyController(
            baseCurrencyData = baseCurrencyData,
            onBaseCurrencyValueChange = onBaseCurrencyValueChange,
            onBaseCurrencySelection = onBaseCurrencySelection,
            modifier = Modifier
                .fillMaxWidth()
        )

        if (converterUiState.exchangeRatesUiState == ExchangeRatesUiState.Loading) {
            LoadingScreen()
        } else {
            ConversionResultsList(
                conversionResultsListItemSize = conversionResultsListItemSize,
                baseCurrencyData = baseCurrencyData,
                exchangeRatesUiState = converterUiState.exchangeRatesUiState,
                exchangeRates = converterUiState.exchangeRates,
                onExchangeRatesRefresh = onExchangeRatesRefresh,
                onConversionEntryDeletion = { conversionEntry ->
                    onConversionEntryDeletion(conversionEntry)
                    onConversionEntryDeletionSnackbarDisplay()
                }
            )
        }
    }
}