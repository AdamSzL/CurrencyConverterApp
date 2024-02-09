package com.example.currencyconverterapp.ui.screens.charts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.data.defaultAvailableCurrencies
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.ui.screens.converter.currencies_dropdown.CurrenciesDropdownMenu
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.diff.ExtraStore
import java.time.LocalDate

@Composable
fun ChartsScreen(
    chartsUiState: ChartsUiState,
    chartEntryModelProducer: ChartEntryModelProducer,
    axisExtraKey: ExtraStore.Key<List<String>>,
    currencies: List<Currency>,
    onBaseCurrencySelection: (Currency) -> Unit,
    onTargetCurrencySelection: (Currency) -> Unit,
    onTimePeriodSelection: (TimePeriod) -> Unit,
    onColumnChartToggle: (Boolean) -> Unit,
    onChartUpdate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(dimensionResource(R.dimen.converter_margin))
        ) {
            CurrenciesDropdownMenu(
                currencies = filterChartCurrencies(currencies, chartsUiState),
                textLabel = R.string.base_currency,
                selectedCurrency = chartsUiState.selectedBaseCurrency,
                onCurrencySelection = onBaseCurrencySelection,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.converter_input_gap)))
            CurrenciesDropdownMenu(
                currencies = filterChartCurrencies(currencies, chartsUiState),
                textLabel = R.string.target_currency,
                selectedCurrency = chartsUiState.selectedTargetCurrency,
                onCurrencySelection = onTargetCurrencySelection,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Switch(
                checked = chartsUiState.isColumnChartEnabled,
                onCheckedChange = {
                    onColumnChartToggle(it)
                },
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.column_chart_switch_text_gap)))
            Text(
                text = stringResource(R.string.column_chart)
            )
        }
        TimePeriodDropdownMenu(
            selectedTimePeriod = chartsUiState.selectedTimePeriod,
            onTimePeriodSelection = onTimePeriodSelection,
        )
        Spacer(modifier = modifier.height(dimensionResource(R.dimen.chart_currency_gap)))
        HistoricalExchangeRatesChart(
            baseCurrency = chartsUiState.selectedBaseCurrency,
            targetCurrency = chartsUiState.selectedTargetCurrency,
            isColumnChartEnabled = chartsUiState.isColumnChartEnabled,
            chartEntryModelProducer = chartEntryModelProducer,
            axisExtraKey = axisExtraKey,
        )
    }
}

fun filterChartCurrencies(currencies: List<Currency>, chartsUiState: ChartsUiState): List<Currency> {
    return currencies.filter { currency ->
        currency.code != chartsUiState.selectedBaseCurrency.code &&
        currency.code != chartsUiState.selectedTargetCurrency.code
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ChartsScreenPreview() {
//    CurrencyConverterAppTheme {
//        ChartsScreen(
//            chartsUiState = ChartsUiState(),
//            currencies = defaultAvailableCurrencies,
//            onBaseCurrencySelection = { },
//            onTargetCurrencySelection = { },
//            onChartUpdate = { },
//            axisExtraKey = ExtraStore.Key(),
//            chartEntryModelProducer = ChartEntryModelProducer(),
//            onColumnChartToggle = { },
//        )
//    }
//}