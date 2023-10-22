package com.example.currencyconverterapp.ui.screens.converter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.ui.screens.converter.currencies_dropdown.CurrenciesDropdownMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCurrencyBottomSheet(
    currencies: List<Currency>,
    selectedTargetCurrency: Currency?,
    sheetScaffoldState: BottomSheetScaffoldState,
    onTargetCurrencySelection: (Currency) -> Unit,
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = sheetScaffoldState,
        sheetContent = {
            Column {
                SheetHeader()
                SheetForm(
                    currencies = currencies,
                    selectedTargetCurrency = selectedTargetCurrency,
                    onTargetCurrencySelection = onTargetCurrencySelection,
                    onCancel = onCancel,
                    onSubmit = onSubmit,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    ) {
        content()
    }
}

@Composable
fun SheetHeader(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(dimensionResource(R.dimen.bottom_sheet_padding))
    ) {
        Text(
            text = stringResource(R.string.add_target_currency),
            style = MaterialTheme.typography.displayMedium,
            modifier = modifier
                .padding(dimensionResource(R.dimen.bottom_sheet_padding))
        )
        Divider()
    }
}

@Composable
fun SheetForm(
    currencies: List<Currency>,
    selectedTargetCurrency: Currency?,
    onTargetCurrencySelection: (Currency) -> Unit,
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.sheet_form_horizontal_padding))
    ) {
        CurrenciesDropdownMenu(
            currencies = currencies,
            textLabel = R.string.target_currency,
            selectedCurrency = selectedTargetCurrency,
            onCurrencySelection = onTargetCurrencySelection
        )
        SheetActionButtons(
            selectedTargetCurrency = selectedTargetCurrency,
            onCancel = onCancel,
            onSubmit = onSubmit
        )
    }
}

@Composable
fun SheetActionButtons(
    selectedTargetCurrency: Currency?,
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(vertical = dimensionResource(R.dimen.bottom_sheet_padding))
    ) {
        OutlinedButton(
            onClick = onCancel,
            border = null
        ) {
            Text(
                text = stringResource(R.string.cancel)
            )
        }
        Button(
            onClick = onSubmit,
            enabled = selectedTargetCurrency != null,
        ) {
            Text(
                text = stringResource(R.string.add)
            )
        }
    }
}