@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.currencyconverterapp.ui.screens.converter.conversion_results

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
import com.example.currencyconverterapp.data.model.Currency
import com.example.currencyconverterapp.data.model.ExchangeRate
import com.example.currencyconverterapp.data.util.defaultAvailableCurrencies
import com.example.currencyconverterapp.data.util.defaultBaseCurrency
import com.example.currencyconverterapp.data.util.defaultBaseCurrencyValue
import com.example.currencyconverterapp.data.util.defaultExchangeRates
import com.example.currencyconverterapp.ui.screens.converter.ExchangeRatesUiState
import com.example.currencyconverterapp.ui.screens.converter.base_controller.BaseControllerUtils.getFlagResourceByCurrencyCode
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun ConversionResultsListItem(
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
        Image(
            painter = painterResource(getFlagResourceByCurrencyCode(context, exchangeRate.code.lowercase())),
            contentDescription = null,
            modifier = Modifier.size(dimensionResource(R.dimen.flag_size)),
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.conversion_result_item_flag_gap)))
        ConversionItemMainContent(
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
    exchangeRatesUiState: ExchangeRatesUiState = ExchangeRatesUiState.Success,
    isLoading: Boolean = false,
) {
    CurrencyConverterAppTheme {
        ConversionResultsListItem(
            exchangeRatesUiState = exchangeRatesUiState,
            baseCurrency = defaultBaseCurrency,
            targetCurrency = defaultAvailableCurrencies.find { it.code == "GBP" }!!,
            baseCurrencyValue = defaultBaseCurrencyValue,
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