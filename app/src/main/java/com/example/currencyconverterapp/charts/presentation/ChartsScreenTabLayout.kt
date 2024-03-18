package com.example.currencyconverterapp.charts.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.charts.presentation.util.ChartsScreenTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartsScreenTabLayout(
    chartController: @Composable () -> Unit,
    chartContent: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember {
        mutableStateOf(ChartsScreenTab.CONTROLLER)
    }
    SingleChoiceSegmentedButtonRow(
        modifier = modifier
            .padding(top = dimensionResource(R.dimen.charts_gap_medium))
    ) {
        ChartsScreenTab.entries.forEachIndexed { index, tab ->
            SegmentedButton(
                selected = selectedTab == tab,
                onClick = {
                    selectedTab = tab
                },
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = ChartsScreenTab.entries.size,
                )
            ) {
                Text(
                    text = tab.label,
                )
            }
        }
    }
    if (selectedTab == ChartsScreenTab.CONTROLLER) {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.charts_gap_big)))
    }
    AnimatedContent(
        targetState = selectedTab
    ) { chartsScreenTab ->
        when (chartsScreenTab) {
            ChartsScreenTab.CONTROLLER -> {
                chartController()
            }
            ChartsScreenTab.CHART -> {
                chartContent()
            }
        }
    }
}