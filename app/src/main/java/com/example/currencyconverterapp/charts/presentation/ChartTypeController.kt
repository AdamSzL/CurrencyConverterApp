package com.example.currencyconverterapp.charts.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartTypeController(
    selectedChartType: ChartType,
    onChartTypeUpdate: (ChartType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.converter_horizontal_margin))
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.chart_type),
            style = MaterialTheme.typography.displaySmall,
        )
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
}

@Preview(showBackground = true)
@Composable
private fun ChartTypeControllerPreview() {
    CurrencyConverterAppTheme {
        ChartTypeController(
            selectedChartType = ChartType.COLUMN,
            onChartTypeUpdate = { }
        )
    }
}