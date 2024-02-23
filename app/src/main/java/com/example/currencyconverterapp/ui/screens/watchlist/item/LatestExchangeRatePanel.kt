package com.example.currencyconverterapp.ui.screens.watchlist.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.ui.screens.charts.DateTimeHandler.getAndFormatTimeDifference
import com.example.currencyconverterapp.ui.screens.charts.NumberUtilities.printDecimalWithoutScientificNotation
import com.example.currencyconverterapp.ui.screens.converter.base_controller.BaseControllerHelpers.getFlagResourceByCurrencyCode
import com.example.currencyconverterapp.ui.screens.watchlist.WatchlistItemCurrencyFlags
import com.example.currencyconverterapp.ui.theme.Montserrat
import kotlinx.coroutines.delay

@Composable
fun BoxScope.LatestExchangeRatePanel(
    baseCurrency: Currency,
    targetCurrency: Currency,
    latestExchangeRate: Double,
    lastUpdatedAt: String,
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
        )

        Text(
            text = "1 ${baseCurrency.code} = ${printDecimalWithoutScientificNotation(latestExchangeRate)} ${targetCurrency.code}",
            style = MaterialTheme.typography.displaySmall,
        )

        Text(
            text = "Updated $timeDifference",
            style = MaterialTheme.typography.labelSmall.copy(fontFamily = Montserrat),
        )
    }
}