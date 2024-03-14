package com.example.currencyconverterapp.charts.presentation

import CustomDropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.charts.data.model.RecentTimePeriod

@Composable
fun RecentTimePeriodDropdownMenu(
    chartsUiState: ChartsUiState,
    onRecentTimePeriodSelection: (RecentTimePeriod) -> Unit,
    modifier: Modifier = Modifier,
) {
    CustomDropdownMenu(
        items = RecentTimePeriod.entries.map { it.label },
        label = R.string.recent_time_period,
        selectedItemLabel = chartsUiState.selectedRecentTimePeriod.label,
        getItemByString = { label -> RecentTimePeriod.getByLabel(label) },
        onItemSelection = onRecentTimePeriodSelection,
        modifier = modifier
    )
}