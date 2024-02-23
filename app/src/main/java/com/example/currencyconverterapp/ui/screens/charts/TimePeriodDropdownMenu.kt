package com.example.currencyconverterapp.ui.screens.charts

import CustomDropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.model.TimePeriod

@Composable
fun TimePeriodDropdownMenu(
    chartsUiState: ChartsUiState,
    onTimePeriodSelection: (TimePeriod) -> Unit,
    modifier: Modifier = Modifier,
) {
    CustomDropdownMenu(
        items = TimePeriod.entries.map { it.label },
        label = R.string.time_period,
        selectedItemLabel = chartsUiState.selectedTimePeriod.label,
        getItemByString = { label -> TimePeriod.getByLabel(label) },
        onItemSelection = onTimePeriodSelection,
        modifier = modifier
    )
}