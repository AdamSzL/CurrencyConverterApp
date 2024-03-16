package com.example.currencyconverterapp.converter.presentation

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.converter.presentation.bottom_sheet.AddCurrencyBottomSheet
import com.example.currencyconverterapp.core.data.model.Currency
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterScreen(
    converterUiState: ConverterUiState,
    availableCurrencies: List<Currency>,
    onExchangeRatesRefresh: () -> Unit,
    onBaseCurrencySelection: (Currency) -> Unit,
    onBaseCurrencyValueChange: (Double) -> Unit,
    onTargetCurrencySelection: (Currency) -> Unit,
    onTargetCurrencyAddition: (Currency, Currency) -> Unit,
    onConversionEntryDeletion: (String) -> Unit,
    onConversionEntryDeletionUndo: () -> Unit,
    onErrorMessageDisplayed: () -> Unit,
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
    val snackbarErrorMessage = stringResource(R.string.error_loading_exchange_rates)

    val scope = rememberCoroutineScope()

    if (converterUiState.exchangeRatesUiState == ExchangeRatesUiState.Error && converterUiState.shouldShowErrorMessage) {
        LaunchedEffect(Unit) {
            scope.launch {
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar(snackbarErrorMessage)
                onErrorMessageDisplayed()
            }
        }
    }

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
            ConverterScreenMainContent(
                converterUiState = converterUiState,
                availableCurrencies = availableCurrencies,
                onExchangeRatesRefresh = onExchangeRatesRefresh,
                onBaseCurrencySelection = onBaseCurrencySelection,
                onBaseCurrencyValueChange = onBaseCurrencyValueChange,
                onConversionEntryDeletion = onConversionEntryDeletion,
                onConversionEntryDeletionSnackbarDisplay = {
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
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
                },
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

data class BaseCurrencyData(
    val currencies: List<Currency>,
    val baseCurrency: Currency,
    val baseCurrencyValue: Double
)