package com.example.currencyconverterapp.ui.screens.loading

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.ui.screens.loading.charts.LoadingChartBaseTargetController
import com.example.currencyconverterapp.ui.screens.loading.charts.LoadingChartWithController
import com.example.currencyconverterapp.ui.screens.loading.converter.LoadingConverterCurrencyController
import com.example.currencyconverterapp.ui.screens.loading.converter.LoadingConverterResultsList

@Composable
fun LoadingScreen(
    type: LoadingScreenType,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        if (type == LoadingScreenType.FULL_CONVERTER || type == LoadingScreenType.PART_CONVERTER) {
            val conversionResultsItems = 5
            if (type == LoadingScreenType.FULL_CONVERTER) {
                LoadingConverterCurrencyController()
            }
            LoadingConverterResultsList(conversionResultsItems)
        } else {
            if (type == LoadingScreenType.FULL_CHARTS) {
                LoadingChartBaseTargetController()
            }
            LoadingChartWithController()
        }
    }
}

@Preview(showBackground = true, name = "FULL_CONVERTER")
@Composable
fun LoadingScreenPreviewFullConverter() {
    LoadingScreen(LoadingScreenType.FULL_CONVERTER)
}

@Preview(showBackground = true, name = "FULL_CHARTS")
@Composable
fun LoadingScreenPreviewFullCharts() {
    LoadingScreen(LoadingScreenType.FULL_CHARTS)
}

@Preview(showBackground = true, name = "PART_CONVERTER")
@Composable
fun LoadingScreenPreviewPartConverter() {
    LoadingScreen(LoadingScreenType.PART_CONVERTER)
}

@Preview(showBackground = true, name = "PART_CHARTS")
@Composable
fun LoadingScreenPreviewPartCharts() {
    LoadingScreen(LoadingScreenType.PART_CHARTS)
}
