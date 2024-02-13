package com.example.currencyconverterapp.ui.screens.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
fun LoadingConverterResultsListItem(
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(dimensionResource(R.dimen.conversion_result_item_margin))
            .height(dimensionResource(R.dimen.conversion_result_loading_item_height))
    ) {
        ShimmerLoadingBox(
            modifier = Modifier
                .fillMaxWidth(0.15f)
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.conversion_result_item_flag_gap)))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.weight(1f)
        ) {
            ShimmerLoadingBox(
                modifier = Modifier
                    .fillMaxWidth(0.2f)
            )
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center,
            ) {
                ShimmerLoadingBox(
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingConverterResultsListItemPreview() {
    LoadingConverterResultsListItem()
}