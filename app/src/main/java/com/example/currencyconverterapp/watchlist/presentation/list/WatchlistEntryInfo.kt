package com.example.currencyconverterapp.watchlist.presentation.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.converter.presentation.util.CurrencyUtils
import com.example.currencyconverterapp.core.data.util.defaultWatchlistItems
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme
import com.example.currencyconverterapp.watchlist.data.model.WatchlistItem

@Composable
fun WatchlistEntryInfo(
    watchlistItem: WatchlistItem,
    modifier: Modifier = Modifier
) {
    with(watchlistItem) {
        val baseCurrencyFormat = CurrencyUtils.getCurrencyFormat(baseCurrency)
        val targetCurrencyFormat = CurrencyUtils.getCurrencyFormat(targetCurrency)

        Column(
            modifier = modifier
        ) {
            Text(
                text = stringResource(
                    R.string.watchlist_entry_title,
                    baseCurrency.name,
                    baseCurrency.symbol,
                    targetCurrency.name,
                    targetCurrency.symbol,
                ),
                style = MaterialTheme.typography.displaySmall,
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.watchlist_item_small_gap)))
            Text(
                text = stringResource(
                    R.string.notification_when,
                    baseCurrencyFormat.format(1),
                    exchangeRateRelation.label.lowercase(),
                    targetCurrencyFormat.format(targetValue)
                ),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WatchlistEntryInfoPreview() {
    CurrencyConverterAppTheme {
        WatchlistEntryInfo(
            watchlistItem = defaultWatchlistItems.first(),
        )
    }
}