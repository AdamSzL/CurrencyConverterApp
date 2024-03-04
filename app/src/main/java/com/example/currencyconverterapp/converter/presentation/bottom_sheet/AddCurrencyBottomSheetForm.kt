package com.example.currencyconverterapp.converter.presentation.bottom_sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.converter.presentation.currencies_dropdown.CurrenciesDropdownMenu
import com.example.currencyconverterapp.core.data.model.Currency

@Composable
fun AddCurrencyBottomSheetForm(
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
