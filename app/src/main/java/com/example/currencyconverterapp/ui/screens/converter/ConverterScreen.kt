package com.example.currencyconverterapp.ui.screens.converter

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.currencyconverterapp.model.Currency

@Composable
fun ConverterScreen(
    converterUiState: ConverterUiState,
    onBaseCurrencySelection: (Currency) -> Unit,
    onBaseCurrencyValueChange: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        ConverterBaseCurrencyInput(
            currencies = converterUiState.availableCurrencies,
            baseCurrency = converterUiState.baseCurrency,
            baseCurrencyValue = converterUiState.baseCurrencyValue,
            onBaseCurrencyValueChange = onBaseCurrencyValueChange,
            onBaseCurrencySelection = onBaseCurrencySelection,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}