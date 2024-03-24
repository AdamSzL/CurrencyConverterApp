@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.currencyconverterapp.converter.presentation.conversion_results

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.converter.presentation.BaseCurrencyData
import com.example.currencyconverterapp.converter.presentation.ExchangeRatesUiState
import com.example.currencyconverterapp.core.data.model.ExchangeRate
import com.example.currencyconverterapp.core.data.util.defaultBaseCurrencyData
import com.example.currencyconverterapp.core.data.util.defaultExchangeRates
import com.example.currencyconverterapp.core.presentation.util.ConversionResultsListItemSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversionResultsList(
    conversionResultsListItemSize: ConversionResultsListItemSize,
    baseCurrencyData: BaseCurrencyData,
    exchangeRatesUiState: ExchangeRatesUiState,
    exchangeRates: List<ExchangeRate>,
    onExchangeRatesRefresh: () -> Unit,
    onConversionEntryDeletion: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberPullToRefreshState()
    if (state.isRefreshing) {
        LaunchedEffect(true) {
            onExchangeRatesRefresh()
            state.endRefresh()
        }
    }
    val scaleFraction = if (state.isRefreshing) 1f else
        LinearOutSlowInEasing.transform(state.progress).coerceIn(0f, 1f)


    Box(
        modifier = modifier
            .nestedScroll(state.nestedScrollConnection)
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.converter_horizontal_margin))
    ) {
        LazyColumn {
            itemsIndexed(
                exchangeRates,
                key = { _, item -> item.id }
            ) { index, exchangeRate ->
                HorizontalDivider()
                SwipeToDismissCurrencyContainer(
                    exchangeRate = exchangeRate,
                    onConversionEntryDeletion = onConversionEntryDeletion,
                ) {
                    ConversionResultsListItem(
                        conversionResultsListItemSize = conversionResultsListItemSize,
                        exchangeRatesUiState = exchangeRatesUiState,
                        baseCurrency = baseCurrencyData.baseCurrency,
                        baseCurrencyValue = baseCurrencyData.baseCurrencyValue.toDouble(),
                        targetCurrency = baseCurrencyData.currencies.find { it.code == exchangeRate.code }!!,
                        exchangeRate = exchangeRate,
                        onExchangeRatesRefresh = onExchangeRatesRefresh,
                        modifier = Modifier
                    )
                }
                if (index == exchangeRates.size - 1) {
                    HorizontalDivider()
                }
            }
        }
        PullToRefreshContainer(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .graphicsLayer(scaleX = scaleFraction, scaleY = scaleFraction),
            state = state
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConversionResultsListPreview(
    isSelectionModeEnabled: Boolean = false,
) {
    ConversionResultsList(
        conversionResultsListItemSize = ConversionResultsListItemSize.DEFAULT,
        baseCurrencyData = defaultBaseCurrencyData,
        exchangeRates = defaultExchangeRates,
        onConversionEntryDeletion = { },
        exchangeRatesUiState = ExchangeRatesUiState.Success,
        onExchangeRatesRefresh = { }
    )
}

@Preview(showBackground = true)
@Composable
fun ConversionResultsListSelectionModePreview() {
    ConversionResultsListPreview(
        isSelectionModeEnabled = true
    )
}