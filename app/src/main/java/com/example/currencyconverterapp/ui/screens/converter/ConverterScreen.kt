package com.example.currencyconverterapp.ui.screens.converter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.ui.screens.DataStateHandler
import com.example.currencyconverterapp.ui.screens.converter.base_controller.BaseCurrencyController
import com.example.currencyconverterapp.ui.screens.converter.conversion_results.ConversionResultsList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterScreen(
    converterUiState: ConverterUiState,
    availableCurrencies: List<Currency>,
    onExchangeRatesUpdate: (Currency) -> Unit,
    onBaseCurrencySelection: (Currency) -> Unit,
    onBaseCurrencyValueChange: (Double) -> Unit,
    onTargetCurrencySelection: (Currency) -> Unit,
    onTargetCurrencyAddition: (Currency, Currency) -> Unit,
    onConversionEntryDeletion: (String) -> Unit,
    onConversionEntryDeletionUndo: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        )
    )
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarMessage = stringResource(R.string.currency_deleted_message)
    val snackbarActionMessage = stringResource(R.string.undo)

    val scope = rememberCoroutineScope()

    AddCurrencyBottomSheet(
        currencies = if (converterUiState.exchangeRatesUiState is ExchangeRatesUiState.Success) {
            availableCurrencies.filter { currency ->
                currency.code !in
                        ((converterUiState.exchangeRatesUiState as ExchangeRatesUiState.Success).exchangeRates.map { rate -> rate.code } + converterUiState.baseCurrency.code)
            }
        } else {
            availableCurrencies
        },
        exchangeRatesUiState = converterUiState.exchangeRatesUiState,
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
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            floatingActionButton = {
                ConverterFloatingActionButton(
                    onClick = {
                        scope.launch { bottomSheetScaffoldState.bottomSheetState.expand() }
                    }
                )
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
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

                DataStateHandler(
                    uiState = converterUiState.exchangeRatesUiState.toString(),
                    errorMessage = R.string.error_loading_exchange_rates,
                    onErrorRetryAction = {
                        onExchangeRatesUpdate(converterUiState.baseCurrency)
                    }
                ) {
                    ConversionResultsList(
                        baseCurrencyData = baseCurrencyData,
                        exchangeRates = (converterUiState.exchangeRatesUiState as ExchangeRatesUiState.Success).exchangeRates,
                        onConversionEntryDeletion = { conversionEntry ->
                            onConversionEntryDeletion(conversionEntry)
                            scope.launch{
                                val result = snackbarHostState
                                    .showSnackbar(
                                        message = snackbarMessage,
                                        actionLabel = snackbarActionMessage,
                                        duration = SnackbarDuration.Short,
                                    )
                                if (result == SnackbarResult.ActionPerformed) {
                                    onConversionEntryDeletionUndo()
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

data class BaseCurrencyData(
    val currencies: List<Currency>,
    val baseCurrency: Currency,
    val baseCurrencyValue: Double
)