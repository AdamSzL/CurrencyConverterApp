package com.example.currencyconverterapp.charts.presentation

import CustomDropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.charts.data.model.RecentTimePeriod
import com.example.currencyconverterapp.charts.data.model.TimePeriodType
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun RecentTimePeriodDropdownMenu(
    selectedTimePeriodType: TimePeriodType,
    onRecentTimePeriodSelection: (RecentTimePeriod) -> Unit,
    modifier: Modifier = Modifier,
) {
    val recentTimePeriod = (selectedTimePeriodType as TimePeriodType.Recent).recentTimePeriod
    CustomDropdownMenu(
        items = RecentTimePeriod.entries.map { it.label },
        label = R.string.recent_time_period,
        selectedItemLabel = recentTimePeriod.label,
        getItemByString = { label -> RecentTimePeriod.getByLabel(label) },
        onItemSelection = onRecentTimePeriodSelection,
        modifier = modifier
    )
}

@Preview
@Composable
private fun RecentTimePeriodDropdownMenuPreview() {
    CurrencyConverterAppTheme {
        RecentTimePeriodDropdownMenu(
            selectedTimePeriodType = TimePeriodType.Recent(RecentTimePeriod.ONE_MONTH),
            onRecentTimePeriodSelection = { }
        )
    }
}