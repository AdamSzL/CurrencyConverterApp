package com.example.currencyconverterapp.ui.screens.loading

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R

@Composable
fun LoadingConverterResultsList(
    numberOfItems: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.converter_margin))
    ) {
        repeat(numberOfItems) {
            HorizontalDivider()
            LoadingConverterResultsListItem()
            if (it == 4) {
                HorizontalDivider()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingConverterResultsListPreview() {
    LoadingConverterResultsList(numberOfItems = 5)
}