package com.example.currencyconverterapp.converter.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.converter.presentation.base_controller.BaseCurrencyController
import com.example.currencyconverterapp.converter.presentation.conversion_results.ConversionResultsList
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.presentation.components.EmptyListInfo
import com.example.currencyconverterapp.core.presentation.components.LoadingScreen
import com.example.currencyconverterapp.core.presentation.util.ConversionResultsListItemSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterScreenMainContent(
    converterUiState: ConverterUiState,
    converterBaseCurrencyValue: String,
    conversionResultsListItemSize: ConversionResultsListItemSize,
    availableCurrencies: List<Currency>,
    onExchangeRatesRefresh: () -> Unit,
    onBaseCurrencySelection: (Currency) -> Unit,
    onBaseCurrencyValueChange: (String) -> Unit,
    onConversionEntryDeletion: (String) -> Unit,
    onConversionEntryDeletionSnackbarDisplay: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        val baseCurrencyData = BaseCurrencyData(
            currencies = availableCurrencies,
            baseCurrency = converterUiState.baseCurrency,
            baseCurrencyValue = converterBaseCurrencyValue,
        )

        BaseCurrencyController(
            baseCurrencyData = baseCurrencyData,
            onBaseCurrencyValueChange = onBaseCurrencyValueChange,
            onBaseCurrencySelection = onBaseCurrencySelection,
        )

        if (converterUiState.exchangeRatesUiState == ExchangeRatesUiState.Loading) {
            LoadingScreen()
        } else {
            AnimatedContent(
                targetState = converterUiState.exchangeRates.isNotEmpty()
            ) { areExchangeRatesNotEmpty ->
                if (areExchangeRatesNotEmpty) {
                    ConversionResultsList(
                        conversionResultsListItemSize = conversionResultsListItemSize,
                        baseCurrencyData = baseCurrencyData,
                        exchangeRatesUiState = converterUiState.exchangeRatesUiState,
                        exchangeRates = converterUiState.exchangeRates,
                        onExchangeRatesRefresh = onExchangeRatesRefresh,
                        onConversionEntryDeletion = { conversionEntry ->
                            onConversionEntryDeletion(conversionEntry)
                            onConversionEntryDeletionSnackbarDisplay()
                        },
                    )
                } else {
                    EmptyListInfo(
                        icon = R.drawable.ic_money,
                        iconDescription = null,
                        text = R.string.converter_empty,
                    )
                }
            }
        }
    }
}