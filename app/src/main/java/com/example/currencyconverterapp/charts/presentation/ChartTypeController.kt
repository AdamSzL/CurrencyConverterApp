package com.example.currencyconverterapp.charts.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconverterapp.charts.data.model.ChartType
import com.example.currencyconverterapp.charts.data.model.TimePeriodType
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartTypeController(
    selectedChartType: ChartType,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        Text(
            text = "Chart Type",
            style = MaterialTheme.typography.displaySmall,
        )
        SingleChoiceSegmentedButtonRow {
            ChartType.entries.forEachIndexed { index, chartType ->
                SegmentedButton(
                    selected = selectedChartType == chartType,
                    onClick = { },
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

@Preview
@Composable
private fun ChartTypeControllerPreview() {
    CurrencyConverterAppTheme {
        ChartTypeController(
            selectedChartType = ChartType.COLUMN
        )
    }
}