package com.example.currencyconverterapp.ui.screens.loading.charts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import com.example.currencyconverterapp.ui.screens.loading.ShimmerLoadingBox

@Composable
fun LoadingChartWithController(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
    ) {
        Spacer(Modifier.height(dimensionResource(R.dimen.loading_charts_item_gap)))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.loading_switch_height))
        ) {
            ShimmerLoadingBox(modifier.width(dimensionResource(R.dimen.loading_switch_width)))
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.column_chart_switch_text_gap)))
            ShimmerLoadingBox(modifier.width(dimensionResource(R.dimen.loading_switch_text_width)))
        }
        Spacer(Modifier.height(dimensionResource(R.dimen.loading_charts_item_gap)))
        Box(
            Modifier
                .padding(dimensionResource(R.dimen.loading_time_period_dropdown_margin))
                .height(dimensionResource(R.dimen.loading_time_period_dropdown_height))
        ) {
            ShimmerLoadingBox(Modifier.fillMaxWidth())
        }
        Spacer(modifier = modifier.height(dimensionResource(R.dimen.chart_currency_gap)))
        Box(
            Modifier
                .padding(dimensionResource(R.dimen.chart_padding))
                .fillMaxHeight(0.9f)
                .fillMaxWidth()
        ) {
            ShimmerLoadingBox(Modifier.fillMaxWidth())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingChartWithControllerPreview() {
    LoadingChartWithController()
}