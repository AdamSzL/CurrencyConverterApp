package com.example.currencyconverterapp.ui.screens.converter

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.ExchangeRate
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme
import java.nio.file.Files.find
import java.text.NumberFormat

@Composable
fun ConversionResultsList(
    baseCurrencyData: BaseCurrencyData,
    exchangeRates: List<ExchangeRate>,
    selectionData: SelectionData,
    selectedConversionEntries: List<String>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.converter_margin))
    ) {
        itemsIndexed(exchangeRates) { index, exchangeRate ->
            Divider()
            ConversionResultsListItem(
                baseCurrency = baseCurrencyData.baseCurrency,
                baseCurrencyValue = baseCurrencyData.baseCurrencyValue,
                targetCurrency = baseCurrencyData.currencies.find { it.code == exchangeRate.code }!!,
                exchangeRate = exchangeRate,
                isSelected = exchangeRate.code in selectedConversionEntries,
                selectionData = selectionData,
                modifier = Modifier
                    .fillMaxWidth()
            )
            if (index == exchangeRates.size - 1) {
                Divider()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ConversionResultsListItem(
    baseCurrency: Currency,
    baseCurrencyValue: Double,
    targetCurrency: Currency,
    exchangeRate: ExchangeRate,
    isSelected: Boolean,
    selectionData: SelectionData,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .combinedClickable(
                onClick = {},
                onLongClick = {
                    selectionData.toggleSelectionMode()
                    selectionData.toggleConversionEntry(exchangeRate.code, !isSelected)
                },
                onDoubleClick = {}
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(dimensionResource(R.dimen.conversion_result_item_margin))
        ) {
            val context = LocalContext.current

            if (selectionData.isSelectionModeEnabled) {
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = { selected ->
                        selectionData.toggleConversionEntry(exchangeRate.code, selected)
                    },
                )
            }

            Image(
                painter = painterResource(getFlagResourceByCurrencyCode(context, exchangeRate.code.lowercase())),
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(R.dimen.flag_size)),
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.conversion_result_item_flag_gap)))
            ConversionMainContent(
                baseCurrency = baseCurrency,
                baseCurrencyValue = baseCurrencyValue,
                targetCurrency = targetCurrency,
                exchangeRate = exchangeRate
            )
        }
    }
}

@Composable
fun RowScope.ConversionMainContent(
    baseCurrency: Currency,
    baseCurrencyValue: Double,
    targetCurrency: Currency,
    exchangeRate: ExchangeRate,
    modifier: Modifier = Modifier
) {
    val targetCurrencyFormat = getCurrencyFormat(targetCurrency)
    val baseCurrencyFormat = getCurrencyFormat(baseCurrency)

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.weight(1f)
    ) {
        Column {
            Text(
                text = targetCurrency.code,
                style = MaterialTheme.typography.displaySmall,
            )
            Text(
                text = targetCurrency.name,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center,
        ) {
            if (exchangeRate.value == null) {
                Box(
                    modifier = Modifier.size(dimensionResource(R.dimen.loading_icon_size))
                ) {
                    CircularProgressIndicator(
                        strokeWidth = dimensionResource(R.dimen.progress_indicator_stroke_width),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        trackColor = MaterialTheme.colorScheme.secondary,
                    )
                }
            } else {
                Text(
                    text = targetCurrencyFormat.format(baseCurrencyValue * exchangeRate.value),
                    style = MaterialTheme.typography.displaySmall,
                )
                Text(
                    text = "${targetCurrencyFormat.format(1)} " +
                            "= ${baseCurrencyFormat.format(1 / exchangeRate.value)}",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

fun getCurrencyFormat(currency: Currency): NumberFormat {
    val currencyFormat = NumberFormat.getCurrencyInstance()
    currencyFormat.maximumFractionDigits = 2
    val targetCurrencyInstance = java.util.Currency.getInstance(currency.code)
    currencyFormat.currency = targetCurrencyInstance
    return currencyFormat
}

@Preview(showBackground = true)
@Composable
fun ConversionResultsListPreview(
    isSelectionModeEnabled: Boolean = false,
) {
    CurrencyConverterAppTheme {
        ConversionResultsList(
            baseCurrencyData = defaultBaseCurrencyData,
            exchangeRates = defaultExchangeRates,
            selectionData = defaultSelectionData.copy(isSelectionModeEnabled = isSelectionModeEnabled),
            selectedConversionEntries = listOf(),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConversionResultsListSelectionModePreview() {
    ConversionResultsListPreview(
        isSelectionModeEnabled = true
    )
}

@Preview(showBackground = true)
@Composable
fun ConversionResultsListItemPreview(
    isLoading: Boolean = false,
    isSelectionModeEnabled: Boolean = false,
    isSelected: Boolean = false,
) {
    CurrencyConverterAppTheme {
        ConversionResultsListItem(
            baseCurrency = defaultBaseCurrency,
            targetCurrency = defaultAvailableCurrencies.find { it.code == "GBP" }!!,
            baseCurrencyValue = defaultBaseCurrencyValue,
            exchangeRate = if (isLoading) {
                defaultExchangeRates.first().copy(value = null)
            } else {
                defaultExchangeRates.first()
            },
            selectionData = defaultSelectionData.copy(isSelectionModeEnabled = isSelectionModeEnabled),
            isSelected = isSelected,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConversionResultsListItemSelectionModePreview() {
    ConversionResultsListItemPreview(
        isSelectionModeEnabled = true
    )
}

@Preview(showBackground = true)
@Composable
fun ConversionResultsListItemSelectionModeSelectedPreview() {
    ConversionResultsListItemPreview(
        isSelectionModeEnabled = true,
        isSelected = true,
    )
}

@Preview(showBackground = true)
@Composable
fun ConversionResultsLoadingListItemPreview() {
    ConversionResultsListItemPreview(
        isLoading = true,
    )
}