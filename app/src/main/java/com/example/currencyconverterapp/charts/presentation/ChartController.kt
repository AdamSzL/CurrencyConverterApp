package com.example.currencyconverterapp.charts.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.charts.data.model.ChartType
import com.example.currencyconverterapp.charts.data.model.TimePeriodType
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.data.util.defaultAvailableCurrencies
import com.example.currencyconverterapp.core.presentation.util.ChartControllerType
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun ChartController(
    chartsUiState: ChartsUiState,
    currencies: List<Currency>,
    type: ChartControllerType,
    onBaseCurrencySelection: (Currency) -> Unit,
    onTargetCurrencySelection: (Currency) -> Unit,
    onTimePeriodTypeUpdate: (TimePeriodType) -> Unit,
    onChartTypeUpdate: (ChartType) -> Unit,
    onBaseAndTargetCurrenciesSwap: () -> Unit,
    onRangeTimePeriodPickerLaunch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(top = dimensionResource(R.dimen.charts_gap_medium))
    ) {
        val weightModifier = Modifier
            .then(
                if (type == ChartControllerType.HORIZONTAL) {
                    Modifier.weight(1f)
                } else {
                    Modifier
                }
            )
        val controllerContent: @Composable () -> Unit = {
            BaseTargetCurrenciesController(
                chartsUiState = chartsUiState,
                currencies = currencies,
                controllerType = type,
                onBaseCurrencySelection = onBaseCurrencySelection,
                onTargetCurrencySelection = onTargetCurrencySelection,
                onBaseAndTargetCurrenciesSwap = onBaseAndTargetCurrenciesSwap,
                modifier = weightModifier
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.charts_gap_big)))

            ChartTypeController(
                selectedChartType = chartsUiState.selectedChartType,
                controllerType = type,
                onChartTypeUpdate = onChartTypeUpdate,
                modifier = weightModifier
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.charts_gap_big)))

            TimePeriodController(
                chartsUiState = chartsUiState,
                controllerType = type,
                onTimePeriodTypeUpdate = onTimePeriodTypeUpdate,
                onRangeTimePeriodPickerLaunch = onRangeTimePeriodPickerLaunch,
                modifier = weightModifier
            )
        }

        if (type == ChartControllerType.HORIZONTAL) {
            Row {
                controllerContent()
            }
        } else {
            controllerContent()
        }
    }
}

@Preview(
    showBackground = true,
    device = "spec:id=reference_phone,shape=Normal,width=411,height=891,unit=dp,dpi=420"
)
@Composable
private fun ChartControllerCompactWidthPreview() {
    CurrencyConverterAppTheme {
        ChartController(
            chartsUiState = ChartsUiState(),
            currencies = defaultAvailableCurrencies,
            type = ChartControllerType.VERTICAL,
            onBaseCurrencySelection = { },
            onTargetCurrencySelection = { },
            onTimePeriodTypeUpdate = { },
            onChartTypeUpdate = { },
            onBaseAndTargetCurrenciesSwap = { },
            onRangeTimePeriodPickerLaunch = { },
        )
    }
}

@Preview(showBackground = true,
    device = "spec:id=reference_foldable,shape=Normal,width=673,height=841,unit=dp,dpi=420"
)
@Composable
private fun ChartControllerMediumWidthPreview() {
    CurrencyConverterAppTheme {
        ChartController(
            chartsUiState = ChartsUiState(),
            currencies = defaultAvailableCurrencies,
            type = ChartControllerType.HORIZONTAL,
            onBaseCurrencySelection = { },
            onTargetCurrencySelection = { },
            onTimePeriodTypeUpdate = { },
            onChartTypeUpdate = { },
            onBaseAndTargetCurrenciesSwap = { },
            onRangeTimePeriodPickerLaunch = { },
        )
    }
}

@Preview(showBackground = true,
    device = "spec:id=reference_tablet,shape=Normal,width=1280,height=800,unit=dp,dpi=240"
)
@Composable
private fun ChartControllerExpandedWidthPreview() {
    CurrencyConverterAppTheme {
        ChartController(
            chartsUiState = ChartsUiState(),
            currencies = defaultAvailableCurrencies,
            type = ChartControllerType.HORIZONTAL,
            onBaseCurrencySelection = { },
            onTargetCurrencySelection = { },
            onTimePeriodTypeUpdate = { },
            onChartTypeUpdate = { },
            onBaseAndTargetCurrenciesSwap = { },
            onRangeTimePeriodPickerLaunch = { },
        )
    }
}
