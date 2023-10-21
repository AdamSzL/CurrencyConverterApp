package com.example.currencyconverterapp.ui.screens.converter.conversion_results

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.data.defaultBaseCurrencyData
import com.example.currencyconverterapp.data.defaultExchangeRates
import com.example.currencyconverterapp.data.defaultSelectionData
import com.example.currencyconverterapp.model.ExchangeRate
import com.example.currencyconverterapp.ui.screens.converter.BaseCurrencyData
import com.example.currencyconverterapp.ui.screens.converter.SelectionData
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun ConversionResultsList(
    baseCurrencyData: BaseCurrencyData,
    exchangeRates: List<ExchangeRate>,
    selectionData: SelectionData,
    selectedConversionEntries: List<String>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.converter_margin))
    ) {
        itemsIndexed(exchangeRates) { index, exchangeRate ->
            Divider()
            ConversionResultsListItem(
                baseCurrency = baseCurrencyData.baseCurrency,
                baseCurrencyValue = baseCurrencyData.baseCurrencyValue,
                targetCurrency = baseCurrencyData.currencies.find { it.code == exchangeRate.code }!!,
                exchangeRate = exchangeRate,
                isSelected = exchangeRate.code in selectedConversionEntries,
                selectionData = selectionData,
                modifier = Modifier
                    .fillMaxWidth()
            )
            if (index == exchangeRates.size - 1) {
                Divider()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConversionResultsListPreview(
    isSelectionModeEnabled: Boolean = false,
) {
    CurrencyConverterAppTheme {
        ConversionResultsList(
            baseCurrencyData = defaultBaseCurrencyData,
            exchangeRates = defaultExchangeRates,
            selectionData = defaultSelectionData.copy(isSelectionModeEnabled = isSelectionModeEnabled),
            selectedConversionEntries = listOf(),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConversionResultsListSelectionModePreview() {
    ConversionResultsListPreview(
        isSelectionModeEnabled = true
    )
}