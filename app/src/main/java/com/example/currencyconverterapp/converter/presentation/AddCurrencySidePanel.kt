package com.example.currencyconverterapp.converter.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.converter.presentation.currencies_dropdown.CurrenciesDropdownMenu
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.data.util.defaultAvailableCurrencies
import com.example.currencyconverterapp.core.data.util.defaultTargetCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCurrencySidePanel(
    converterUiState: ConverterUiState,
    currencies: List<Currency>,
    onTargetCurrencySelection: (Currency) -> Unit,
    onAddCurrencySidePanelToggle: () -> Unit,
    onTargetCurrencyAddition: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .width(IntrinsicSize.Min)
            .fillMaxHeight()
            .clip(
                RoundedCornerShape(
                    topStart = dimensionResource(R.dimen.side_panel_rounded_corner),
                    bottomStart = dimensionResource(R.dimen.side_panel_rounded_corner)
                )
            )
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .padding(dimensionResource(R.dimen.converter_horizontal_margin))
    ) {
        CurrenciesDropdownMenu(
            currencies = currencies,
            textLabel = R.string.target_currency,
            selectedCurrency = converterUiState.selectedTargetCurrency,
            onCurrencySelection = onTargetCurrencySelection,
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    onTargetCurrencyAddition()
                    onAddCurrencySidePanelToggle()
                },
                enabled = converterUiState.selectedTargetCurrency != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.add)
                )
            }
            OutlinedButton(
                onClick = onAddCurrencySidePanelToggle,
                border = null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.cancel)
                )
            }
        }
    }
}

@Preview
@PreviewLightDark
@Composable
private fun AddCurrencySidePanelPreview() {
    AddCurrencySidePanel(
        converterUiState = ConverterUiState(
            selectedTargetCurrency = defaultTargetCurrency
        ),
        currencies = defaultAvailableCurrencies,
        onTargetCurrencySelection = { },
        onAddCurrencySidePanelToggle = { },
        onTargetCurrencyAddition = { }
    )
}