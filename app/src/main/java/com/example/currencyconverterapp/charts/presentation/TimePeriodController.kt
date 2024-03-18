package com.example.currencyconverterapp.charts.presentation

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.charts.data.model.RecentTimePeriod
import com.example.currencyconverterapp.charts.data.model.TimePeriodType
import com.example.currencyconverterapp.core.presentation.util.ChartControllerType
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun TimePeriodController(
    chartsUiState: ChartsUiState,
    controllerType: ChartControllerType,
    onTimePeriodTypeUpdate: (TimePeriodType) -> Unit,
    onRangeTimePeriodPickerLaunch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = if (controllerType == ChartControllerType.VERTICAL) {
            Alignment.End
        } else {
            Alignment.CenterHorizontally
        },
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.converter_horizontal_margin))
            .fillMaxWidth()
    ) {

        val segmentedButtonRowWithLabelContent: @Composable () -> Unit = {
            Text(
                text = stringResource(R.string.time_period),
                style = MaterialTheme.typography.displaySmall,
            )
            if (controllerType == ChartControllerType.HORIZONTAL) {
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.charts_gap_medium)))
            }
            TimePeriodTypeSegmentedButtonRow(
                chartsUiState = chartsUiState,
                onTimePeriodTypeUpdate = onTimePeriodTypeUpdate
            )
        }
        if (controllerType == ChartControllerType.VERTICAL) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                segmentedButtonRowWithLabelContent()
            }
        } else {
            segmentedButtonRowWithLabelContent()
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.charts_gap_big)))

        if (chartsUiState.selectedTimePeriodType is TimePeriodType.Recent) {
            RecentTimePeriodDropdownMenu(
                selectedTimePeriodType = chartsUiState.selectedTimePeriodType,
                onRecentTimePeriodSelection = {
                    onTimePeriodTypeUpdate(TimePeriodType.Recent(it))
                },
                modifier = Modifier.width(dimensionResource(R.dimen.recent_time_period_dropdown_width))
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val rangeTimePeriodType = chartsUiState.selectedTimePeriodType as TimePeriodType.Range
                Text(
                    text = "${rangeTimePeriodType.start} - ${rangeTimePeriodType.end}",
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.horizontalScroll(rememberScrollState())
                )
                TextButton(
                    onClick = onRangeTimePeriodPickerLaunch,
                ) {
                    Text(
                        text = stringResource(R.string.change),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TimePeriodControllerDateRangePreview() {
    CurrencyConverterAppTheme {
        TimePeriodController(
            chartsUiState = ChartsUiState(
                selectedTimePeriodType = TimePeriodType.Range("From", "To")
            ),
            controllerType = ChartControllerType.HORIZONTAL,
            onTimePeriodTypeUpdate = { },
            onRangeTimePeriodPickerLaunch = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TimePeriodControllerRecentPreview() {
    CurrencyConverterAppTheme {
        TimePeriodController(
            chartsUiState = ChartsUiState(
                selectedTimePeriodType = TimePeriodType.Recent(RecentTimePeriod.ONE_MONTH)
            ),
            controllerType = ChartControllerType.HORIZONTAL,
            onTimePeriodTypeUpdate = { },
            onRangeTimePeriodPickerLaunch = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TimePeriodControllerDateRangeVerticalPreview() {
    CurrencyConverterAppTheme {
        TimePeriodController(
            chartsUiState = ChartsUiState(
                selectedTimePeriodType = TimePeriodType.Range("From", "To")
            ),
            controllerType = ChartControllerType.VERTICAL,
            onTimePeriodTypeUpdate = { },
            onRangeTimePeriodPickerLaunch = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TimePeriodControllerRecentVerticalPreview() {
    CurrencyConverterAppTheme {
        TimePeriodController(
            chartsUiState = ChartsUiState(
                selectedTimePeriodType = TimePeriodType.Recent(RecentTimePeriod.ONE_MONTH)
            ),
            controllerType = ChartControllerType.VERTICAL,
            onTimePeriodTypeUpdate = { },
            onRangeTimePeriodPickerLaunch = { }
        )
    }
}
