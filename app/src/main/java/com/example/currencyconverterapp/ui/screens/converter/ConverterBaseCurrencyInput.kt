package com.example.currencyconverterapp.ui.screens.converter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterBaseCurrencyInput(
    currencies: List<Currency>,
    baseCurrency: Currency,
    baseCurrencyValue: Double,
    onBaseCurrencySelection: (Currency) -> Unit,
    onBaseCurrencyValueChange: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(dimensionResource(R.dimen.converter_input_margin))
    ) {
        CurrenciesDropdownMenu(
            currencies = currencies,
            baseCurrency = baseCurrency,
            onBaseCurrencySelection = onBaseCurrencySelection,
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.converter_input_gap)))
        BaseCurrencyValueTextField(
            baseCurrencyValue = baseCurrencyValue,
            onBaseCurrencyValueChange = onBaseCurrencyValueChange,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseCurrencyValueTextField(
    baseCurrencyValue: Double,
    onBaseCurrencyValueChange: (Double) -> Unit,
) {
    val pattern = remember { Regex("\\d+\\.\\d+") }

    OutlinedTextField(
        value = baseCurrencyValue.toString(),
        singleLine = true,
        onValueChange = {
            if (it.matches(pattern)) {
                onBaseCurrencyValueChange(it.toDouble())
            }
        },
        label = { Text(stringResource(R.string.value)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrenciesDropdownMenu(
    currencies: List<Currency>,
    baseCurrency: Currency,
    onBaseCurrencySelection: (Currency) -> Unit,
) {
    var expanded by remember { mutableStateOf(true) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        BaseCurrencyTextField(
            baseCurrency = baseCurrency,
            expanded = expanded,
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            currencies.forEach { currency ->
                CurrencyDropdownMenuItem(
                    currency = currency,
                    onItemClicked = {
                        onBaseCurrencySelection(currency)
                        expanded = false
                    },
                )
            }
        }
    }
}

@SuppressLint("DiscouragedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBoxScope.BaseCurrencyTextField(
    baseCurrency: Currency,
    expanded: Boolean,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        modifier = modifier.menuAnchor(),
        readOnly = true,
        singleLine = true,
        value = baseCurrency.code,
        onValueChange = {},
        label = {
            Text(stringResource(R.string.base_currency))
        },
        leadingIcon = {
            val resource = getFlagResourceByCurrencyCode(LocalContext.current, baseCurrency.code.lowercase())
            Image(
                painter = painterResource(resource),
                colorFilter = determineColorFilter(resource, MaterialTheme.colorScheme.onBackground),
                contentDescription = baseCurrency.name,
            )
        },
        trailingIcon = {
            ExposedDropdownMenuDefaults
                .TrailingIcon(expanded = expanded)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyDropdownMenuItem(
    onItemClicked: () -> Unit,
    currency: Currency,
) {
    DropdownMenuItem(
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val resource = getFlagResourceByCurrencyCode(LocalContext.current, currency.code.lowercase())
                    Image(
                        painter = painterResource(resource),
                        colorFilter = determineColorFilter(resource, MaterialTheme.colorScheme.onBackground),
                        contentDescription = currency.name,
                    )
                    Spacer(
                        modifier = Modifier
                            .width(dimensionResource(R.dimen.dropdown_menu_item_gap))
                    )
                    Text(
                        text = currency.code,
                        style = MaterialTheme.typography.displaySmall,
                    )
                }
                Text(
                    text = currency.name,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        },
        onClick = onItemClicked,
        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
    )
}

fun determineColorFilter(
    resource: Int,
    color: Color,
): ColorFilter? {
    return if (resource == R.drawable.flag) {
        ColorFilter.tint(color)
    } else {
        null
    }
}

@SuppressLint("DiscouragedApi")
fun getFlagResourceByCurrencyCode(
    context: Context,
    code: String
): Int {
    val flagResourceId = context.resources.getIdentifier(
        code,
        "drawable",
        context.packageName,
    )
    return if (flagResourceId == 0) R.drawable.flag else flagResourceId
}

@Preview(showBackground = true)
@Composable
fun ConverterBaseCurrencyInputPreview() {
    CurrencyConverterAppTheme {
        ConverterBaseCurrencyInput(
            currencies = defaultAvailableCurrencies,
            baseCurrency = defaultBaseCurrency,
            baseCurrencyValue = defaultBaseCurrencyValue,
            onBaseCurrencySelection = { },
            onBaseCurrencyValueChange = { },
        )
    }
}