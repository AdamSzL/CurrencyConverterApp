package com.example.currencyconverterapp.converter.presentation.base_controller

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.data.util.defaultBaseCurrency

@Composable
fun CurrencyValueTextField(
    currency: Currency,
    currencyValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    shouldShowLabel: Boolean = true,
) {
    OutlinedTextField(
        value = currencyValue,
        singleLine = true,
        onValueChange = { input ->
            onValueChange(if (input.toDoubleOrNull() != null) input else currencyValue)
        },
        label = {
            if (shouldShowLabel) {
                Text(stringResource(R.string.value))
            }
        },
        leadingIcon = {
            Text(
                text = currency.symbolNative,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.currency_value_text_field_symbol_margin))
            )
        },
        textStyle = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Normal),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun CurrencyValueTextFieldPreview() {
    CurrencyValueTextField(
        currency = defaultBaseCurrency.copy(symbolNative = "AMM"),
        currencyValue = "0.2",
        onValueChange = { },
    )
}