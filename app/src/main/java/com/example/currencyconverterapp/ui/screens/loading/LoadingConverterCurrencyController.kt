package com.example.currencyconverterapp.ui.screens.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R

@Composable
fun LoadingConverterCurrencyController(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(dimensionResource(R.dimen.converter_margin))
            .height(dimensionResource(R.dimen.base_currency_controller_loading_height))
    ) {
        ShimmerLoadingBox(
            Modifier
                .weight(3f)
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.converter_input_gap)))
        ShimmerLoadingBox(
            Modifier
                .weight(2f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingConverterCurrencyControllerPreview() {
    LoadingConverterCurrencyController()
}