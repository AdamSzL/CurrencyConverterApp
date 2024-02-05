package com.example.currencyconverterapp.ui.screens.charts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.data.defaultAvailableCurrencies
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.ui.screens.converter.currencies_dropdown.CurrenciesDropdownMenu
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun ChartsScreen(
    chartsUiState: ChartsUiState,
    currencies: List<Currency>,
    onBaseCurrencySelection: (Currency) -> Unit,
    onTargetCurrencySelection: (Currency) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.padding(dimensionResource(R.dimen.converter_margin))
        ) {
            CurrenciesDropdownMenu(
                currencies = filterChartCurrencies(currencies, chartsUiState),
                textLabel = R.string.base_currency,
                selectedCurrency = chartsUiState.selectedBaseCurrency,
                onCurrencySelection = onBaseCurrencySelection,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.converter_input_gap)))
            CurrenciesDropdownMenu(
                currencies = filterChartCurrencies(currencies, chartsUiState),
                textLabel = R.string.target_currency,
                selectedCurrency = chartsUiState.selectedTargetCurrency,
                onCurrencySelection = onTargetCurrencySelection,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

fun filterChartCurrencies(currencies: List<Currency>, chartsUiState: ChartsUiState): List<Currency> {
    return currencies.filter { currency ->
        currency.code != chartsUiState.selectedBaseCurrency.code &&
        currency.code != chartsUiState.selectedTargetCurrency.code
    }
}

@Preview(showBackground = true)
@Composable
fun ChartsScreenPreview() {
    CurrencyConverterAppTheme {
        ChartsScreen(
            chartsUiState = ChartsUiState(),
            currencies = defaultAvailableCurrencies,
            onBaseCurrencySelection = { },
            onTargetCurrencySelection = { }
        )
    }
}