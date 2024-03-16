package com.example.currencyconverterapp.charts.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
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
import com.example.currencyconverterapp.charts.presentation.util.DateTimeUtils.convertDateTimeToDate
import com.example.currencyconverterapp.charts.presentation.util.DateTimeUtils.getCurrentDate
import com.example.currencyconverterapp.charts.presentation.util.DateTimeUtils.subtractTimePeriodFromDate
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePeriodController(
    chartsUiState: ChartsUiState,
    onTimePeriodTypeUpdate: (TimePeriodType) -> Unit,
    onRangeTimePeriodPickerLaunch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.converter_horizontal_margin))
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.time_period_type),
                style = MaterialTheme.typography.displaySmall,
            )
            SingleChoiceSegmentedButtonRow {
                SegmentedButton(
                    selected = chartsUiState.selectedTimePeriodType is TimePeriodType.Recent,
                    onClick = {
                        onTimePeriodTypeUpdate(TimePeriodType.Recent(RecentTimePeriod.ONE_MONTH))
                    },
                    shape = SegmentedButtonDefaults.itemShape(
                        index = 0,
                        count = 2,
                    )
                ) {
                    Text(
                        text = stringResource(R.string.recent)
                    )
                }

                SegmentedButton(
                    selected = chartsUiState.selectedTimePeriodType is TimePeriodType.Range,
                    onClick = {
                        if (chartsUiState.selectedTimePeriodType is TimePeriodType.Recent) {
                            val currentDate = getCurrentDate()
                            onTimePeriodTypeUpdate(
                                TimePeriodType.Range(
                                    convertDateTimeToDate(
                                        dateTime = subtractTimePeriodFromDate(
                                            currentDate,
                                            if (chartsUiState.selectedTimePeriodType.recentTimePeriod != RecentTimePeriod.ONE_DAY) {
                                                chartsUiState.selectedTimePeriodType.recentTimePeriod
                                            } else {
                                                RecentTimePeriod.ONE_MONTH
                                            }
                                        )
                                    ),
                                    convertDateTimeToDate(currentDate)
                                )
                            )
                        }
                    },
                    shape = SegmentedButtonDefaults.itemShape(
                        index = 1,
                        count = 2,
                    )
                ) {
                    Text(
                        text = stringResource(R.string.range)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.charts_gap_big)))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.time_period),
                style = MaterialTheme.typography.displaySmall,
            )

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
}

@Preview(showBackground = true)
@Composable
private fun TimePeriodControllerDateRangePreview() {
    CurrencyConverterAppTheme {
        TimePeriodController(
            chartsUiState = ChartsUiState(
                selectedTimePeriodType = TimePeriodType.Range("From", "To")
            ),
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
            onTimePeriodTypeUpdate = { },
            onRangeTimePeriodPickerLaunch = { }
        )
    }
}
