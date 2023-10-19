package com.example.currencyconverterapp.ui.screens.converter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.currencyconverterapp.model.Currency
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterScreen(
    converterUiState: ConverterUiState,
    onBaseCurrencySelection: (Currency) -> Unit,
    onBaseCurrencyValueChange: (Double) -> Unit,
    onTargetCurrencySelection: (Currency) -> Unit,
    onTargetCurrencyAddition: (Currency) -> Unit,
    modifier: Modifier = Modifier
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        )
    )

    val scope = rememberCoroutineScope()

    AddCurrencyBottomSheet(
        currencies = converterUiState.availableCurrencies,
        selectedTargetCurrency = converterUiState.selectedTargetCurrency,
        sheetScaffoldState = bottomSheetScaffoldState,
        onTargetCurrencySelection = onTargetCurrencySelection,
        onCancel = {
            scope.launch {
                bottomSheetScaffoldState.bottomSheetState.hide()
            }
        },
        onSubmit = {
            onTargetCurrencyAddition(converterUiState.selectedTargetCurrency)
            scope.launch {
                bottomSheetScaffoldState.bottomSheetState.hide()
            }
        },
        modifier = modifier,
    ) {
        Scaffold(
            floatingActionButton = {
                ConverterFloatingActionButton(
                    onClick = {
                        scope.launch { bottomSheetScaffoldState.bottomSheetState.expand() }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
            ) {
                BaseCurrencyController(
                    currencies = converterUiState.availableCurrencies,
                    baseCurrency = converterUiState.baseCurrency,
                    baseCurrencyValue = converterUiState.baseCurrencyValue,
                    onBaseCurrencyValueChange = onBaseCurrencyValueChange,
                    onBaseCurrencySelection = onBaseCurrencySelection,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                when (converterUiState.exchangeRatesStatus) {
                    is ExchangeRatesStatus.Error -> ExchangeRatesErrorScreen()
                    is ExchangeRatesStatus.Loading -> ExchangeRatesLoadingScreen()
                    is ExchangeRatesStatus.Success -> ConversionResultsList(
                        currencies = converterUiState.availableCurrencies,
                        baseCurrency = converterUiState.baseCurrency,
                        baseCurrencyValue = converterUiState.baseCurrencyValue,
                        exchangeRates = converterUiState.exchangeRatesStatus.exchangeRates,
                    )
                }
            }
        }
    }
}

@Composable
fun ExchangeRatesErrorScreen(
    modifier: Modifier = Modifier
) {

}

@Composable
fun ExchangeRatesLoadingScreen(
    modifier: Modifier = Modifier
) {

}