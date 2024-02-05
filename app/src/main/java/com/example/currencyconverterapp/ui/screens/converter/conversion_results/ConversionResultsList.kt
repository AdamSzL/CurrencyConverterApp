@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.currencyconverterapp.ui.screens.converter.conversion_results

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.data.defaultBaseCurrencyData
import com.example.currencyconverterapp.data.defaultExchangeRates
import com.example.currencyconverterapp.model.ExchangeRate
import com.example.currencyconverterapp.ui.screens.converter.BaseCurrencyData
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme
import kotlinx.coroutines.delay

@Composable
fun ConversionResultsList(
    baseCurrencyData: BaseCurrencyData,
    exchangeRates: List<ExchangeRate>,
    onConversionEntryDeletion: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.converter_margin))
    ) {
        itemsIndexed(
            exchangeRates,
            key = { _, item -> item.id }
        ) { index, exchangeRate ->
            HorizontalDivider()
            SwipeToDismissCurrencyContainer(
                exchangeRate = exchangeRate,
                onConversionEntryDeletion = onConversionEntryDeletion,
                modifier = Modifier.fillMaxWidth()
            ) {
                ConversionResultsListItem(
                    baseCurrency = baseCurrencyData.baseCurrency,
                    baseCurrencyValue = baseCurrencyData.baseCurrencyValue,
                    targetCurrency = baseCurrencyData.currencies.find { it.code == exchangeRate.code }!!,
                    exchangeRate = exchangeRate,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            if (index == exchangeRates.size - 1) {
                HorizontalDivider()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDismissCurrencyContainer(
    exchangeRate: ExchangeRate,
    onConversionEntryDeletion: (String) -> Unit,
    modifier: Modifier = Modifier,
    animationDuration: Int = 500,
    content: @Composable () -> Unit,
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        }
    )
    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onConversionEntryDeletion(exchangeRate.code)
        }
    }
    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut(),
        modifier = modifier
    ) {
        SwipeToDismissBox(
            state = dismissState,
            enableDismissFromEndToStart = true,
            enableDismissFromStartToEnd = false,
            backgroundContent = {
                val color = when (dismissState.dismissDirection) {
                    SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.errorContainer
                    else -> Color.Transparent
                }
                if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                    Spacer(Modifier.weight(1f))
                    Box(
                        contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier
                            .fillMaxWidth(if (dismissState.progress == 1.0f) 0.0f else dismissState.progress)
                            .fillMaxWidth(dismissState.progress)
                            .fillMaxHeight()
                            .background(color)
                            .padding(dimensionResource(R.dimen.swipe_background_padding))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = null
                        )
                    }
                }
            }
        ) {
            content()
        }
    }
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
            onConversionEntryDeletion = { }
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