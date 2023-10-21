package com.example.currencyconverterapp.ui.screens.converter.base_controller

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.data.defaultBaseCurrencyData
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.ui.screens.converter.BaseCurrencyData
import com.example.currencyconverterapp.ui.screens.converter.base_controller.BaseControllerHelpers.determineColorFilter
import com.example.currencyconverterapp.ui.screens.converter.base_controller.BaseControllerHelpers.getFlagResourceByCurrencyCode
import com.example.currencyconverterapp.ui.screens.converter.currencies_dropdown.CurrenciesDropdownMenu
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun BaseCurrencyController(
    baseCurrencyData: BaseCurrencyData,
    onBaseCurrencySelection: (Currency) -> Unit,
    onBaseCurrencyValueChange: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(dimensionResource(R.dimen.converter_margin))
    ) {
        CurrenciesDropdownMenu(
            currencies = baseCurrencyData.currencies,
            textLabel = R.string.base_currency,
            selectedCurrency = baseCurrencyData.baseCurrency,
            onCurrencySelection = onBaseCurrencySelection,
            modifier = Modifier.weight(3f),
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.converter_input_gap)))
        CurrencyValueTextField(
            currency = baseCurrencyData.baseCurrency,
            currencyValue = baseCurrencyData.baseCurrencyValue,
            onValueChange = onBaseCurrencyValueChange,
            modifier = Modifier.weight(2f),
        )
    }
}

@SuppressLint("DiscouragedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBoxScope.BaseCurrencyTextField(
    baseCurrency: Currency,
    @StringRes label: Int,
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
            Text(stringResource(label))
        },
        textStyle = MaterialTheme.typography.displaySmall,
        leadingIcon = {
            val resource = getFlagResourceByCurrencyCode(LocalContext.current, baseCurrency.code.lowercase())
            Image(
                painter = painterResource(resource),
                colorFilter = determineColorFilter(resource, MaterialTheme.colorScheme.onBackground),
                contentDescription = baseCurrency.name,
                modifier = Modifier.size(dimensionResource(R.dimen.small_flag_size))
            )
        },
        trailingIcon = {
            ExposedDropdownMenuDefaults
                .TrailingIcon(expanded = expanded)
        },
    )
}

@Preview(showBackground = true)
@Composable
fun BaseCurrencyControllerPreview() {
    CurrencyConverterAppTheme {
        BaseCurrencyController(
            baseCurrencyData = defaultBaseCurrencyData,
            onBaseCurrencySelection = { },
            onBaseCurrencyValueChange = { },
        )
    }
}