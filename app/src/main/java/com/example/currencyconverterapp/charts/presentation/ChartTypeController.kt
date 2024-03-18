package com.example.currencyconverterapp.charts.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.charts.data.model.ChartType
import com.example.currencyconverterapp.core.presentation.util.ChartControllerType
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartTypeController(
    selectedChartType: ChartType,
    controllerType: ChartControllerType,
    onChartTypeUpdate: (ChartType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.converter_horizontal_margin))
    ) {
        val chartTypeControllerContent: @Composable () -> Unit = {
            Text(
                text = stringResource(R.string.chart_type),
                style = MaterialTheme.typography.displaySmall,
            )
            if (controllerType == ChartControllerType.HORIZONTAL) {
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.charts_gap_medium)))
            }
            SingleChoiceSegmentedButtonRow {
                ChartType.entries.forEachIndexed { index, chartType ->
                    SegmentedButton(
                        selected = selectedChartType == chartType,
                        onClick = {
                            onChartTypeUpdate(chartType)
                        },
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = ChartType.entries.size
                        )
                    ) {
                        Text(
                            text = chartType.label,
                        )
                    }
                }
            }
        }

        if (controllerType == ChartControllerType.VERTICAL) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                chartTypeControllerContent()
            }
        } else {
            chartTypeControllerContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChartTypeControllerHorizontalPreview() {
    CurrencyConverterAppTheme {
        ChartTypeController(
            selectedChartType = ChartType.COLUMN,
            controllerType = ChartControllerType.HORIZONTAL,
            onChartTypeUpdate = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChartTypeControllerVerticalPreview() {
    CurrencyConverterAppTheme {
        ChartTypeController(
            selectedChartType = ChartType.COLUMN,
            controllerType = ChartControllerType.VERTICAL,
            onChartTypeUpdate = { }
        )
    }
}
