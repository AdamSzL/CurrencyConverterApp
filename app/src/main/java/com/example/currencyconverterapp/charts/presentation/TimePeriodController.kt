package com.example.currencyconverterapp.charts.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.charts.data.model.RecentTimePeriod
import com.example.currencyconverterapp.charts.data.model.TimePeriodType
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePeriodController(
    chartsUiState: ChartsUiState,
    selectedTimePeriodType: TimePeriodType,
    onRecentTimePeriodSelection: (RecentTimePeriod) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        Text(
            text = "Time Period Type",
            style = MaterialTheme.typography.displaySmall,
        )
        SingleChoiceSegmentedButtonRow {
            TimePeriodType.entries.forEachIndexed { index, timePeriodType ->
                SegmentedButton(
                    selected = selectedTimePeriodType == timePeriodType,
                    onClick = {},
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = TimePeriodType.entries.size
                    )
                ) {
                    Text(
                        text = timePeriodType.label,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (selectedTimePeriodType == TimePeriodType.RECENT) {
            RecentTimePeriodDropdownMenu(
                chartsUiState = chartsUiState,
                onRecentTimePeriodSelection = onRecentTimePeriodSelection,
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Button(
                        onClick = { Log.d("XXX", "Open date range picker") },
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text(
                            text = "Select Date Range"
                        )
                    }
                }
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "15 Nov. - 12 Dec.",
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
//            DateRangePicker()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TimePeriodControllerDateRangePreview() {
    CurrencyConverterAppTheme {
        TimePeriodController(
            chartsUiState = ChartsUiState(),
            selectedTimePeriodType = TimePeriodType.RANGE,
            onRecentTimePeriodSelection = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TimePeriodControllerRecentPreview() {
    CurrencyConverterAppTheme {
        TimePeriodController(
            chartsUiState = ChartsUiState(),
            selectedTimePeriodType = TimePeriodType.RECENT,
            onRecentTimePeriodSelection = { }
        )
    }
}
