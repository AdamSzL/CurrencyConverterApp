package com.example.currencyconverterapp.charts.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.charts.data.model.RecentTimePeriod
import com.example.currencyconverterapp.charts.presentation.util.DateTimeUtils.getDateFromMilliseconds
import com.example.currencyconverterapp.charts.presentation.util.DateTimeUtils.subtractTimePeriodFromDate
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RangeTimePeriodPicker(
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val dateRangePickerState = rememberDateRangePickerState(
        selectableDates = PastOrPresentSelectableDates
    )

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DatePickerDefaults.colors().containerColor)
                .padding(
                    start = dimensionResource(R.dimen.charts_gap_big),
                    end = dimensionResource(R.dimen.charts_gap_big)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onDismiss,
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(R.string.close)
                )
            }
            TextButton(
                onClick = {
                    onSave(
                        getDateFromMilliseconds(dateRangePickerState.selectedStartDateMillis!!),
                        getDateFromMilliseconds(dateRangePickerState.selectedEndDateMillis!!)
                    )
                    onDismiss()
                },
                enabled = dateRangePickerState.selectedEndDateMillis != null
                        && getDateFromMilliseconds(dateRangePickerState.selectedStartDateMillis!!)
                        != getDateFromMilliseconds(dateRangePickerState.selectedEndDateMillis!!)
            ) {
                Text(
                    text = stringResource(R.string.save)
                )
            }
        }

        DateRangePicker(
            state = dateRangePickerState,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
object PastOrPresentSelectableDates: SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        val dateStart = subtractTimePeriodFromDate(
            date = Clock.System.now().toString(),
            recentTimePeriod = RecentTimePeriod.ONE_YEAR,
        )
        return utcTimeMillis <= Clock.System.now().toEpochMilliseconds()
                && utcTimeMillis >= Instant.parse(dateStart).toEpochMilliseconds()
    }

    override fun isSelectableYear(year: Int): Boolean {
        val currentYear = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year
        return year == currentYear || year == currentYear - 1
    }
}

@Preview(showBackground = true)
@Composable
private fun RangeTimePeriodPickerPreview() {
    CurrencyConverterAppTheme {
        RangeTimePeriodPicker(
            onDismiss = { },
            onSave = { _, _ -> }
        )
    }
}