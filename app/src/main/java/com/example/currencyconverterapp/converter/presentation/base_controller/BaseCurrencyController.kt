package com.example.currencyconverterapp.converter.presentation.base_controller

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.converter.presentation.BaseCurrencyData
import com.example.currencyconverterapp.converter.presentation.currencies_dropdown.CurrenciesDropdownMenu
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.data.util.defaultBaseCurrencyData

@Composable
fun BaseCurrencyController(
    baseCurrencyData: BaseCurrencyData,
    onBaseCurrencySelection: (Currency) -> Unit,
    onBaseCurrencyValueChange: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(
                horizontal = dimensionResource(R.dimen.converter_horizontal_margin),
                vertical = dimensionResource(R.dimen.converter_vertical_margin)
            )
    ) {
        CurrenciesDropdownMenu(
            currencies = baseCurrencyData.currencies,
            textLabel = R.string.base_currency,
            selectedCurrency = baseCurrencyData.baseCurrency,
            onCurrencySelection = onBaseCurrencySelection,
            modifier = Modifier
                .weight(1f)
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.converter_input_gap)))
        CurrencyValueTextField(
            currency = baseCurrencyData.baseCurrency,
            currencyValue = baseCurrencyData.baseCurrencyValue,
            onValueChange = onBaseCurrencyValueChange,
            modifier = Modifier
                .weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BaseCurrencyControllerPreview() {
    BaseCurrencyController(
        baseCurrencyData = defaultBaseCurrencyData,
        onBaseCurrencySelection = { },
        onBaseCurrencyValueChange = { },
    )
}
