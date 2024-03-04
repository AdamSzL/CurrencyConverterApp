package com.example.currencyconverterapp.converter.presentation.bottom_sheet

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.core.data.model.Currency

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
            enabled = selectedTargetCurrency != null
        ) {
            Text(
                text = stringResource(R.string.add)
            )
        }
    }
}
