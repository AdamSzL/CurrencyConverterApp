package com.example.currencyconverterapp.ui.screens.converter.currencies_dropdown

import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.ui.screens.converter.base_controller.BaseCurrencyTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrenciesDropdownMenu(
    currencies: List<Currency>,
    @StringRes textLabel: Int,
    selectedCurrency: Currency,
    onCurrencySelection: (Currency) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier,
    ) {
        BaseCurrencyTextField(
            baseCurrency = selectedCurrency,
            label = textLabel,
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
                        onCurrencySelection(currency)
                        expanded = false
                    },
                )
            }
        }
    }
}