package com.example.currencyconverterapp.ui.screens.converter.conversion_results

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.data.defaultAvailableCurrencies
import com.example.currencyconverterapp.data.defaultBaseCurrency
import com.example.currencyconverterapp.data.defaultBaseCurrencyValue
import com.example.currencyconverterapp.data.defaultExchangeRates
import com.example.currencyconverterapp.data.defaultSelectionData
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.ExchangeRate
import com.example.currencyconverterapp.ui.screens.converter.SelectionData
import com.example.currencyconverterapp.ui.screens.converter.base_controller.BaseControllerHelpers.getFlagResourceByCurrencyCode
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
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
                    if (!selectionData.isSelectionModeEnabled) {
                        selectionData.toggleConversionEntry(exchangeRate.code, !isSelected)
                    }
                    selectionData.toggleSelectionMode()
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
                CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                    Checkbox(
                        checked = isSelected,
                        onCheckedChange = { selected ->
                            selectionData.toggleConversionEntry(exchangeRate.code, selected)
                        },
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.conversion_result_item_flag_gap)))
                }
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