package com.example.currencyconverterapp.converter.presentation.bottom_sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.currencyconverterapp.R

@Composable
fun AddCurrencyBottomSheetHeader(
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
        HorizontalDivider()
    }
}

