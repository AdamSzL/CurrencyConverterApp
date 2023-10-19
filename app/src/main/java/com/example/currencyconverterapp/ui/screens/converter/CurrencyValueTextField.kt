package com.example.currencyconverterapp.ui.screens.converter

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.model.Currency

@Composable
fun CurrencyValueTextField(
    currency: Currency,
    currencyValue: Double,
    onValueChange: (Double) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = currencyValue.toString(),
        singleLine = true,
        onValueChange = { input ->
            onValueChange(input.toDouble())
        },
        label = { Text(stringResource(R.string.value)) },
        textStyle = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Normal),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        modifier = modifier
    )
}