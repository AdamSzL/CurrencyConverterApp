package com.example.currencyconverterapp.ui.screens.loading

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
fun LoadingChartBaseTargetController(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(dimensionResource(R.dimen.converter_margin))
            .height(dimensionResource(R.dimen.chart_loading_base_target_controller_height))
    ) {
        ShimmerLoadingBox(Modifier.weight(1f))
        Spacer(Modifier.width(dimensionResource(R.dimen.swap_icon_margin)))
        ShimmerLoadingBox(Modifier.width(dimensionResource(R.dimen.swap_icon_size_loading)))
        Spacer(Modifier.width(dimensionResource(R.dimen.swap_icon_margin)))
        ShimmerLoadingBox(Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingChartBaseTargetControllerPreview() {
    LoadingChartBaseTargetController()
}