package com.example.currencyconverterapp.charts.presentation

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.charts.data.model.ChartType
import com.example.currencyconverterapp.charts.data.model.RecentTimePeriod
import com.example.currencyconverterapp.charts.data.model.TimePeriodType
import com.example.currencyconverterapp.charts.presentation.util.DateTimeUtils.formatDateByTimePeriodType
import com.example.currencyconverterapp.charts.presentation.util.NumberUtils.getRoundingByDifference
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.presentation.util.ChartsScreenContentType
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
import com.patrykandpatrick.vico.core.component.text.textComponent
import com.patrykandpatrick.vico.core.dimensions.MutableDimensions
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.diff.ExtraStore

@Composable
fun HistoricalExchangeRatesChart(
    baseCurrency: Currency,
    targetCurrency: Currency,
    chartsScreenContentType: ChartsScreenContentType,
    chartType: ChartType,
    selectedTimePeriodType: TimePeriodType,
    chartEntryModelProducer: ChartEntryModelProducer,
    axisExtraKey: ExtraStore.Key<List<String>>,
    modifier: Modifier = Modifier,
) {

    val startAxis = rememberStartAxis(
        valueFormatter = { value, _ ->
            val difference = chartEntryModelProducer.getModel()!!.maxY - chartEntryModelProducer.getModel()!!.minY
            "%.${getRoundingByDifference(difference)}f".format(value)
        },
        title = "1 ${baseCurrency.code} = X ${targetCurrency.code}",
        titleComponent = textComponent {
            color = MaterialTheme.colorScheme.primary.toArgb()
            margins = MutableDimensions(5f, 0f)
        }
    )

    val bottomAxis = rememberBottomAxis(
        valueFormatter = { value, _ ->
            val axisData = chartEntryModelProducer.getModel()!!.extraStore.getOrNull(axisExtraKey)
            if (axisData != null) {
                val labelDate = axisData[value.toInt() % axisData.size]
                formatDateByTimePeriodType(labelDate, selectedTimePeriodType)
            } else {
                value.toString()
            }
        },
        labelRotationDegrees = -90f,
        title =
            if (selectedTimePeriodType is TimePeriodType.Recent && selectedTimePeriodType.recentTimePeriod == RecentTimePeriod.ONE_DAY) {
                stringResource(R.string.hour)
            } else {
                stringResource(R.string.day)
            },
        titleComponent = textComponent {
            color = MaterialTheme.colorScheme.primary.toArgb()
            margins = MutableDimensions(0f, 5f)
        }
    )

    val axisValuesOverrider = AxisValuesOverrider.adaptiveYValues(yFraction = 1f)

    val marker = rememberMarker()

    Chart(
        chart = if (chartType == ChartType.COLUMN) columnChart(
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
        marker = marker,
        startAxis = startAxis,
        bottomAxis = bottomAxis,
        modifier = modifier
            .padding(
                dimensionResource(
                    if (chartsScreenContentType == ChartsScreenContentType.FULL) {
                        R.dimen.chart_padding
                    } else {
                        R.dimen.charts_padding_small
                    }
                )
            )
            .fillMaxHeight()
    )
}