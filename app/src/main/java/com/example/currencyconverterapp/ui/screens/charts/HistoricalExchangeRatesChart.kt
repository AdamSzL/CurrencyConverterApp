package com.example.currencyconverterapp.ui.screens.charts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.dimensionResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.ui.screens.charts.DateTimeHandler.formatDateByTimePeriod
import com.example.currencyconverterapp.ui.screens.charts.NumberUtilities.getRoundingByDifference
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.core.DefaultAlpha
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.diff.ExtraStore

@Composable
fun HistoricalExchangeRatesChart(
    baseCurrency: Currency,
    targetCurrency: Currency,
    isColumnChartEnabled: Boolean,
    selectedTimePeriod: TimePeriod,
    chartEntryModelProducer: ChartEntryModelProducer,
    axisExtraKey: ExtraStore.Key<List<String>>,
) {
    val startAxis = rememberStartAxis(
        valueFormatter = { value, _ ->
            val difference = chartEntryModelProducer.getModel()!!.maxY - chartEntryModelProducer.getModel()!!.minY
            "%.${getRoundingByDifference(difference)}f".format(value)
        }
    )
    val bottomAxis = rememberBottomAxis(
        valueFormatter = { value, _ ->
            val axisData = chartEntryModelProducer.getModel()!!.extraStore.get(axisExtraKey)
            val labelDate = axisData[value.toInt() % axisData.size]
            formatDateByTimePeriod(labelDate, selectedTimePeriod)
        },
        labelRotationDegrees = -90f,
    )
    val axisValuesOverrider = AxisValuesOverrider.adaptiveYValues(yFraction = 1f)
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Chart(
            chart = if (isColumnChartEnabled) columnChart(
                columns = listOf(LineComponent(
                    color = MaterialTheme.colorScheme.primary.toArgb(),
                    thicknessDp = dimensionResource(R.dimen.column_chart_width).value,
                )),
                axisValuesOverrider = axisValuesOverrider,
            ) else lineChart(
                lines = listOf(LineChart.LineSpec(
                    lineColor = MaterialTheme.colorScheme.primary.toArgb(),
                    lineBackgroundShader = DynamicShaders.fromBrush(
                        brush = Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                                MaterialTheme.colorScheme.primary.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_END),
                            )
                        )
                    ),
                )),
                axisValuesOverrider = axisValuesOverrider,
            ),
            chartModelProducer = chartEntryModelProducer,
            startAxis = startAxis,
            bottomAxis = bottomAxis,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.chart_padding))
                .fillMaxHeight(0.9f)
        )
    }
}