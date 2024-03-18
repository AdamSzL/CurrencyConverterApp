package com.example.currencyconverterapp.charts.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.charts.data.model.RecentTimePeriod
import com.example.currencyconverterapp.charts.data.model.TimePeriodType
import com.example.currencyconverterapp.charts.presentation.util.DateTimeUtils
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePeriodTypeSegmentedButtonRow(
    chartsUiState: ChartsUiState,
    onTimePeriodTypeUpdate: (TimePeriodType) -> Unit,
    modifier: Modifier = Modifier
) {
    SingleChoiceSegmentedButtonRow(
        modifier = modifier
    ) {
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
                    val currentDate = DateTimeUtils.getCurrentDate()
                    onTimePeriodTypeUpdate(
                        TimePeriodType.Range(
                            DateTimeUtils.convertDateTimeToDate(
                                dateTime = DateTimeUtils.subtractTimePeriodFromDate(
                                    currentDate,
                                    if (chartsUiState.selectedTimePeriodType.recentTimePeriod != RecentTimePeriod.ONE_DAY) {
                                        chartsUiState.selectedTimePeriodType.recentTimePeriod
                                    } else {
                                        RecentTimePeriod.ONE_MONTH
                                    }
                                )
                            ),
                            DateTimeUtils.convertDateTimeToDate(currentDate)
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

@Preview(showBackground = true)
@Composable
private fun TimePeriodTypeSegmentedButtonRowPreview() {
    CurrencyConverterAppTheme {
        TimePeriodTypeSegmentedButtonRow(
            chartsUiState = ChartsUiState(),
            onTimePeriodTypeUpdate = { }
        )
    }
}