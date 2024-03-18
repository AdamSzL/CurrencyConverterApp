package com.example.currencyconverterapp.watchlist.presentation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.core.data.util.defaultWatchlistItems
import com.example.currencyconverterapp.core.presentation.util.WatchlistEntrySize
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme
import com.example.currencyconverterapp.watchlist.data.model.WatchlistItem

@Composable
fun WatchlistEntry(
    watchlistItem: WatchlistItem,
    watchlistEntrySize: WatchlistEntrySize,
    onWatchlistItemClicked: (String) -> Unit,
    onWatchlistItemDeletion: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = {
            onWatchlistItemClicked(watchlistItem.id)
        },
        modifier = modifier
            .fillMaxWidth()
    ) {
        val context = LocalContext.current
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.watchlist_entry_list_margin))
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.watchlist_item_small_gap)),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
            ) {
                WatchlistEntryFlags(
                    watchlistEntrySize = watchlistEntrySize,
                    context = context,
                    baseCurrency = watchlistItem.baseCurrency,
                    targetCurrency = watchlistItem.targetCurrency,
                    modifier = Modifier
                )
                WatchlistEntryInfo(
                    watchlistItem = watchlistItem,
                    watchlistEntrySize = watchlistEntrySize,
                    modifier = Modifier
                        .fillMaxHeight()
                )
            }
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.watchlist_item_small_gap)))
            FilledTonalIconButton(
                onClick = {
                    onWatchlistItemDeletion(watchlistItem.id)
                }
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_watchlist_remove),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                    contentDescription = stringResource(R.string.watchlist_remove)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@PreviewLightDark
@Composable
fun WatchlistEntryPreview() {
    CurrencyConverterAppTheme {
        WatchlistEntry(
            watchlistItem = defaultWatchlistItems.first(),
            watchlistEntrySize = WatchlistEntrySize.DEFAULT,
            onWatchlistItemClicked = { },
            onWatchlistItemDeletion = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WatchlistEntryBigPreview() {
    CurrencyConverterAppTheme {
        WatchlistEntry(
            watchlistItem = defaultWatchlistItems.first(),
            watchlistEntrySize = WatchlistEntrySize.BIG,
            onWatchlistItemClicked = { },
            onWatchlistItemDeletion = { }
        )
    }
}
