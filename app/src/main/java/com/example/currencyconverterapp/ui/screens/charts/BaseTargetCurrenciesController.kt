package com.example.currencyconverterapp.ui.screens.charts

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.ui.screens.converter.currencies_dropdown.CurrenciesDropdownMenu

@Composable
fun BaseTargetCurrenciesController(
    chartsUiState: ChartsUiState,
    currencies: List<Currency>,
    onBaseCurrencySelection: (Currency) -> Unit,
    onTargetCurrencySelection: (Currency) -> Unit,
    onChartUpdate: () -> Unit,
    onBaseAndTargetCurrenciesSwap: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(dimensionResource(R.dimen.converter_margin))
    ) {
        CurrenciesDropdownMenu(
            currencies = filterChartCurrencies(currencies, chartsUiState),
            textLabel = R.string.base_currency,
            selectedCurrency = chartsUiState.selectedBaseCurrency,
            onCurrencySelection = {
                onBaseCurrencySelection(it)
                onChartUpdate()
            },
            modifier = Modifier.weight(1f)
        )
        Image(
            painter = painterResource(R.drawable.ic_swap),
            contentDescription = stringResource(R.string.swap_currencies),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
            modifier = Modifier
                .padding(dimensionResource(R.dimen.swap_icon_margin))
                .clickable {
                    onBaseAndTargetCurrenciesSwap()
                    onChartUpdate()
                }
        )
        CurrenciesDropdownMenu(
            currencies = filterChartCurrencies(currencies, chartsUiState),
            textLabel = R.string.target_currency,
            selectedCurrency = chartsUiState.selectedTargetCurrency,
            onCurrencySelection = {
                onTargetCurrencySelection(it)
                onChartUpdate()
            },
            modifier = Modifier.weight(1f)
        )
    }
}