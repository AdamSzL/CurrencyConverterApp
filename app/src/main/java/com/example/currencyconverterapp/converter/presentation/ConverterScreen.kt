package com.example.currencyconverterapp.converter.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import com.example.currencyconverterapp.core.presentation.util.ConversionResultsListItemSize
import com.example.currencyconverterapp.core.presentation.util.ConverterAddCurrencyContainerType
import com.example.currencyconverterapp.core.presentation.util.FabSize
import com.example.currencyconverterapp.core.presentation.util.FloatingActionButtonType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterScreen(
    converterUiState: ConverterUiState,
    converterBaseCurrencyValue: String,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    isAddCurrencyPanelVisible: Boolean,
    fabType: FloatingActionButtonType,
    conversionResultsListItemSize: ConversionResultsListItemSize,
    converterAddCurrencyContainerType: ConverterAddCurrencyContainerType,
    availableCurrencies: List<Currency>,
    converterScreenActions: ConverterScreenActions,
    onAddCurrencySidePanelToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarMessage = stringResource(R.string.currency_deleted_message)
    val snackbarActionMessage = stringResource(R.string.undo)

    val scope = rememberCoroutineScope()

    if (converterUiState.snackbarMessage != "") {
        LaunchedEffect(converterUiState.snackbarMessage) {
            if (converterUiState.snackbarMessage == snackbarMessage) {
                snackbarHostState.currentSnackbarData?.dismiss()
                val result = snackbarHostState
                    .showSnackbar(
                        message = snackbarMessage,
                        actionLabel = snackbarActionMessage,
                        duration = SnackbarDuration.Short,
                    )
                if (result == SnackbarResult.ActionPerformed) {
                    converterScreenActions.onConversionEntryDeletionUndo()
                }
            } else {
                snackbarHostState.showSnackbar(converterUiState.snackbarMessage)
            }
            converterScreenActions.onSnackbarDisplay("")
        }
    }

    with (converterScreenActions) {
        val converterScreenMainContent: @Composable RowScope.() -> Unit = {
            ConverterScreenMainContent(
                converterUiState = converterUiState,
                converterBaseCurrencyValue = converterBaseCurrencyValue,
                conversionResultsListItemSize = conversionResultsListItemSize,
                availableCurrencies = availableCurrencies,
                onExchangeRatesRefresh = onExchangeRatesRefresh,
                onBaseCurrencySelection = onBaseCurrencySelection,
                onBaseCurrencyValueChange = onBaseCurrencyValueChange,
                onConversionEntryDeletion = onConversionEntryDeletion,
                onConversionEntryDeletionSnackbarDisplay = {
                    onSnackbarDisplay(snackbarMessage)
                },
                modifier = Modifier.weight(1f)
            )
        }

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
            modifier = modifier
        ) { paddingValues ->
            val filteredCurrencies = availableCurrencies.filter { currency ->
                    currency.code !in
                            (converterUiState.exchangeRates.map { rate -> rate.code } + converterUiState.baseCurrency.code)
            }
            Row {
                if (converterAddCurrencyContainerType == ConverterAddCurrencyContainerType.BOTTOM_SHEET) {
                    AddCurrencyBottomSheet(
                        currencies = filteredCurrencies,
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
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        converterScreenMainContent()
                    }
                } else {
                    converterScreenMainContent()
                    AnimatedVisibility(visible = isAddCurrencyPanelVisible) {
                        AddCurrencySidePanel(
                            converterUiState = converterUiState,
                            currencies = filteredCurrencies,
                            onTargetCurrencySelection = onTargetCurrencySelection,
                            onAddCurrencySidePanelToggle = onAddCurrencySidePanelToggle,
                            onTargetCurrencyAddition = {
                                onTargetCurrencyAddition(converterUiState.baseCurrency, converterUiState.selectedTargetCurrency!!)
                            }
                        )
                    }
                }
            }
        }
    }
}

data class BaseCurrencyData(
    val currencies: List<Currency>,
    val baseCurrency: Currency,
    val baseCurrencyValue: String
)