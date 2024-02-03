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
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.data.defaultAvailableCurrencies
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.ui.screens.converter.base_controller.BaseCurrencyController
import com.example.currencyconverterapp.ui.screens.converter.conversion_results.ConversionResultsList
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterScreen(
    converterUiState: ConverterUiState,
    availableCurrencies: List<Currency>,
    onBaseCurrencySelection: (Currency) -> Unit,
    onBaseCurrencyValueChange: (Double) -> Unit,
    onTargetCurrencySelection: (Currency) -> Unit,
    onTargetCurrencyAddition: (Currency, Currency) -> Unit,
    onSelectionModeToggle: () -> Unit,
    onConversionEntryToggle: (String, Boolean) -> Unit,
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
        currencies = availableCurrencies.filter { currency ->
            currency.code !in
                    (converterUiState.exchangeRates.map { rate -> rate.code } + converterUiState.baseCurrency.code)
        },
        selectedTargetCurrency = converterUiState.selectedTargetCurrency,
        sheetScaffoldState = bottomSheetScaffoldState,
        onTargetCurrencySelection = onTargetCurrencySelection,
        onCancel = {
            scope.launch {
                bottomSheetScaffoldState.bottomSheetState.hide()
            }
        },
        onSubmit = {
            onTargetCurrencyAddition(converterUiState.baseCurrency, converterUiState.selectedTargetCurrency!!)
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
                val baseCurrencyData = BaseCurrencyData(
                    currencies = availableCurrencies,
                    baseCurrency = converterUiState.baseCurrency,
                    baseCurrencyValue = converterUiState.baseCurrencyValue
                )

                val selectionData = SelectionData(
                    isSelectionModeEnabled = converterUiState.isSelectionModeEnabled,
                    toggleSelectionMode = onSelectionModeToggle,
                    toggleConversionEntry = onConversionEntryToggle,
                )

                BaseCurrencyController(
                    baseCurrencyData = baseCurrencyData,
                    onBaseCurrencyValueChange = onBaseCurrencyValueChange,
                    onBaseCurrencySelection = onBaseCurrencySelection,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                ConversionResultsList(
                    baseCurrencyData = baseCurrencyData,
                    exchangeRates = converterUiState.exchangeRates,
                    selectionData = selectionData,
                    selectedConversionEntries = converterUiState.selectedConversionEntryCodes,
                )
            }
        }
    }
}

data class BaseCurrencyData(
    val currencies: List<Currency>,
    val baseCurrency: Currency,
    val baseCurrencyValue: Double
)

data class SelectionData(
    val isSelectionModeEnabled: Boolean,
    val toggleSelectionMode: () -> Unit,
    val toggleConversionEntry: (String, Boolean) -> Unit,
)

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