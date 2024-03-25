package com.example.currencyconverterapp.watchlist.presentation.item.latest_exchange_rate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.charts.presentation.util.DateTimeUtils.getAndFormatTimeDifference
import com.example.currencyconverterapp.charts.presentation.util.NumberUtils.printDecimalWithoutScientificNotation
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.presentation.util.WatchlistItemScreenContentType
import com.example.currencyconverterapp.ui.theme.Montserrat
import com.example.currencyconverterapp.watchlist.presentation.item.WatchlistItemCurrencyFlags
import kotlinx.coroutines.delay

@Composable
fun BoxScope.LatestExchangeRatePanel(
    baseCurrency: Currency,
    targetCurrency: Currency,
    latestExchangeRate: Double,
    lastUpdatedAt: String,
    watchlistItemScreenContentType: WatchlistItemScreenContentType,
    modifier: Modifier = Modifier,
) {
    var timeDifference by remember(lastUpdatedAt) { mutableStateOf(getAndFormatTimeDifference(lastUpdatedAt)) }
    LaunchedEffect(lastUpdatedAt) {
        while(true) {
            delay(1000L)
            timeDifference = getAndFormatTimeDifference(lastUpdatedAt)
        }
    }

    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.exchange_rate_panel_small_gap)),
        modifier = modifier
            .align(Alignment.BottomEnd)
    ) {
        val context = LocalContext.current
        WatchlistItemCurrencyFlags(
            context = context,
            baseCurrency = baseCurrency,
            targetCurrency = targetCurrency,
            watchlistItemScreenContentType = watchlistItemScreenContentType,
        )

        Text(
            text = "1 ${baseCurrency.code} = ${printDecimalWithoutScientificNotation(latestExchangeRate)} ${targetCurrency.code}",
            style = if (watchlistItemScreenContentType == WatchlistItemScreenContentType.SMALL_FONT) {
                MaterialTheme.typography.displaySmall
            } else {
                MaterialTheme.typography.displayMedium
            },
        )

        Text(
            text = "Updated $timeDifference",
            style = if (watchlistItemScreenContentType == WatchlistItemScreenContentType.SMALL_FONT) {
                MaterialTheme.typography.labelSmall.copy(fontFamily = Montserrat)
            } else {
                   MaterialTheme.typography.labelMedium.copy(fontFamily = Montserrat)
            },
        )
    }
}