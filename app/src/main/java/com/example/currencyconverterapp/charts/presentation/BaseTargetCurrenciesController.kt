package com.example.currencyconverterapp.charts.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.charts.presentation.util.ChartsUtils.filterChartCurrencies
import com.example.currencyconverterapp.converter.presentation.currencies_dropdown.CurrenciesDropdownMenu
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.data.util.defaultAvailableCurrencies
import com.example.currencyconverterapp.core.presentation.components.SwapButton
import com.example.currencyconverterapp.core.presentation.util.ChartControllerType
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun BaseTargetCurrenciesController(
    chartsUiState: ChartsUiState,
    currencies: List<Currency>,
    controllerType: ChartControllerType,
    onBaseCurrencySelection: (Currency) -> Unit,
    onTargetCurrencySelection: (Currency) -> Unit,
    onBaseAndTargetCurrenciesSwap: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(
                horizontal = dimensionResource(R.dimen.converter_horizontal_margin),
                vertical = dimensionResource(R.dimen.converter_vertical_margin)
            )
    ) {
        val weightModifier = Modifier
            .then(
                if (controllerType == ChartControllerType.VERTICAL) {
                    Modifier.weight(1f)
                } else {
                    Modifier
                }
            )
        val baseTargetCurrenciesController: @Composable () -> Unit = {
            CurrenciesDropdownMenu(
                currencies = filterChartCurrencies(currencies, chartsUiState),
                textLabel = R.string.base_currency,
                selectedCurrency = chartsUiState.selectedBaseCurrency,
                onCurrencySelection = {
                    onBaseCurrencySelection(it)
                },
                modifier = weightModifier
            )
            if (controllerType == ChartControllerType.VERTICAL) {
                SwapButton(
                    contentDescription = R.string.swap_currencies,
                    onClick = onBaseAndTargetCurrenciesSwap,
                )
            }
            CurrenciesDropdownMenu(
                currencies = filterChartCurrencies(currencies, chartsUiState),
                textLabel = R.string.target_currency,
                selectedCurrency = chartsUiState.selectedTargetCurrency,
                onCurrencySelection = {
                    onTargetCurrencySelection(it)
                },
                modifier = weightModifier
            )
        }

        if (controllerType == ChartControllerType.VERTICAL) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.swap_icon_margin)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                baseTargetCurrenciesController()
            }
        } else {
            Box {
                Column {
                    baseTargetCurrenciesController()
                }
                SwapButton(
                    contentDescription = R.string.swap_currencies,
                    onClick = onBaseAndTargetCurrenciesSwap,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .offset(x = dimensionResource(R.dimen.swap_icon_offset)),
                    rotation = 90.0f
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BaseTargetCurrenciesControllerHorizontalPreview() {
    CurrencyConverterAppTheme {
        BaseTargetCurrenciesController(
            chartsUiState = ChartsUiState(),
            currencies = defaultAvailableCurrencies,
            controllerType = ChartControllerType.HORIZONTAL,
            onBaseCurrencySelection = { },
            onTargetCurrencySelection = { },
            onBaseAndTargetCurrenciesSwap = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BaseTargetCurrenciesControllerVerticalPreview() {
    CurrencyConverterAppTheme {
        BaseTargetCurrenciesController(
            chartsUiState = ChartsUiState(),
            currencies = defaultAvailableCurrencies,
            controllerType = ChartControllerType.VERTICAL,
            onBaseCurrencySelection = { },
            onTargetCurrencySelection = { },
            onBaseAndTargetCurrenciesSwap = { }
        )
    }
}
