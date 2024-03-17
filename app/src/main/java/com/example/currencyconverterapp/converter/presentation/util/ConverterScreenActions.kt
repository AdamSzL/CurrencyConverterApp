package com.example.currencyconverterapp.converter.presentation.util

import com.example.currencyconverterapp.converter.presentation.ConverterViewModel
import com.example.currencyconverterapp.core.data.model.Currency

class ConverterScreenActions(
    val onExchangeRatesRefresh: () -> Unit,
    val onBaseCurrencySelection: (Currency) -> Unit,
    val onBaseCurrencyValueChange: (Double) -> Unit,
    val onTargetCurrencySelection: (Currency) -> Unit,
    val onTargetCurrencyAddition: (Currency, Currency) -> Unit,
    val onConversionEntryDeletion: (String) -> Unit,
    val onConversionEntryDeletionUndo: () -> Unit,
    val onErrorMessageDisplayed: () -> Unit,
)

fun constructConverterScreenActions(
    converterViewModel: ConverterViewModel,
    onExchangeRatesRefresh: () -> Unit,
): ConverterScreenActions {
    return ConverterScreenActions(
        onExchangeRatesRefresh = onExchangeRatesRefresh,
        onBaseCurrencySelection = converterViewModel::selectBaseCurrency,
        onBaseCurrencyValueChange = converterViewModel::setBaseCurrencyValue,
        onTargetCurrencySelection = converterViewModel::selectTargetCurrency,
        onTargetCurrencyAddition = converterViewModel::addTargetCurrency,
        onConversionEntryDeletion = converterViewModel::deleteConversionEntry,
        onConversionEntryDeletionUndo = converterViewModel::undoConversionEntryDeletion,
        onErrorMessageDisplayed = converterViewModel::errorMessageDisplayed,
    )
}