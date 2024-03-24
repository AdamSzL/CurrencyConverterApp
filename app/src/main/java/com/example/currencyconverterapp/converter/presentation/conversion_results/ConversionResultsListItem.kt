@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.currencyconverterapp.converter.presentation.conversion_results

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.converter.presentation.ExchangeRatesUiState
import com.example.currencyconverterapp.converter.presentation.util.BaseControllerUtils.getFlagResourceByCurrencyCode
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.data.model.ExchangeRate
import com.example.currencyconverterapp.core.data.util.defaultAvailableCurrencies
import com.example.currencyconverterapp.core.data.util.defaultBaseCurrency
import com.example.currencyconverterapp.core.data.util.defaultBaseCurrencyValue
import com.example.currencyconverterapp.core.data.util.defaultExchangeRates
import com.example.currencyconverterapp.core.presentation.util.ConversionResultsListItemSize
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun ConversionResultsListItem(
    conversionResultsListItemSize: ConversionResultsListItemSize,
    exchangeRatesUiState: ExchangeRatesUiState,
    baseCurrency: Currency,
    baseCurrencyValue: Double,
    targetCurrency: Currency,
    exchangeRate: ExchangeRate,
    onExchangeRatesRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(dimensionResource(R.dimen.conversion_result_item_margin))
    ) {
        val context = LocalContext.current
        val flagSizeModifier = if (conversionResultsListItemSize == ConversionResultsListItemSize.DEFAULT) {
            Modifier.size(dimensionResource(R.dimen.flag_size))
        } else {
            Modifier.size(dimensionResource(R.dimen.big_flag_size))
        }
        Image(
            painter = painterResource(getFlagResourceByCurrencyCode(context, exchangeRate.code.lowercase())),
            contentDescription = null,
            modifier = flagSizeModifier
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.conversion_result_item_flag_gap)))
        ConversionItemMainContent(
            conversionResultsListItemSize = conversionResultsListItemSize,
            exchangeRatesUiState = exchangeRatesUiState,
            baseCurrency = baseCurrency,
            baseCurrencyValue = baseCurrencyValue,
            targetCurrency = targetCurrency,
            exchangeRate = exchangeRate,
            onExchangeRatesRefresh = onExchangeRatesRefresh,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ConversionResultsListItemPreview(
    conversionResultsListItemSize: ConversionResultsListItemSize = ConversionResultsListItemSize.DEFAULT,
    exchangeRatesUiState: ExchangeRatesUiState = ExchangeRatesUiState.Success,
    isLoading: Boolean = false,
) {
    CurrencyConverterAppTheme {
        ConversionResultsListItem(
            conversionResultsListItemSize = conversionResultsListItemSize,
            exchangeRatesUiState = exchangeRatesUiState,
            baseCurrency = defaultBaseCurrency,
            targetCurrency = defaultAvailableCurrencies.find { it.code == "GBP" }!!,
            baseCurrencyValue = defaultBaseCurrencyValue.toDouble(),
            exchangeRate = if (isLoading) {
                defaultExchangeRates.first().copy(value = null)
            } else {
                defaultExchangeRates.first()
            },
            onExchangeRatesRefresh = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConversionResultsListItemPreviewUiStateError() {
    ConversionResultsListItemPreview(
        exchangeRatesUiState = ExchangeRatesUiState.Error
    )
}

@Preview(showBackground = true)
@Composable
fun ConversionResultsListItemPreviewUiStateErrorValueNull() {
    ConversionResultsListItemPreview(
        exchangeRatesUiState = ExchangeRatesUiState.Error,
        isLoading = true,
    )
}

@Preview(showBackground = true)
@Composable
fun ConversionResultsLoadingListItemPreview() {
    ConversionResultsListItemPreview(
        isLoading = true,
    )
}

@Preview(showBackground = true)
@Composable
fun ConversionResultsListItemBigSizePreview() {
    ConversionResultsListItemPreview(
        conversionResultsListItemSize = ConversionResultsListItemSize.BIG
    )
}
