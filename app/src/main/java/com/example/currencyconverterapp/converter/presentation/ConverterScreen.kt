package com.example.currencyconverterapp.converter.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.converter.presentation.bottom_sheet.AddCurrencyBottomSheet
import com.example.currencyconverterapp.converter.presentation.util.ConverterScreenActions
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.presentation.util.FabSize
import com.example.currencyconverterapp.core.presentation.util.FloatingActionButtonType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterScreen(
    converterUiState: ConverterUiState,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    fabType: FloatingActionButtonType,
    availableCurrencies: List<Currency>,
    converterScreenActions: ConverterScreenActions,
    modifier: Modifier = Modifier
) {
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
                converterScreenActions.onErrorMessageDisplayed()
            }
        }
    }

    with (converterScreenActions) {
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
                    if (fabType == FloatingActionButtonType.BOTTOM_RIGHT) {
                        ConverterFloatingActionButton(
                            size = FabSize.SMALL,
                            onClick = {
                                scope.launch { bottomSheetScaffoldState.bottomSheetState.expand() }
                            }
                        )
                    }
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
}

data class BaseCurrencyData(
    val currencies: List<Currency>,
    val baseCurrency: Currency,
    val baseCurrencyValue: Double
)