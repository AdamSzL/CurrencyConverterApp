package com.example.currencyconverterapp.watchlist.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.core.data.util.defaultWatchlistItems
import com.example.currencyconverterapp.core.presentation.util.WatchlistEntrySize
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme
import com.example.currencyconverterapp.watchlist.data.model.WatchlistItem

@Composable
fun WatchlistEntryList(
    watchlistItems: List<WatchlistItem>,
    watchlistEntrySize: WatchlistEntrySize,
    onWatchlistItemClicked: (String) -> Unit,
    onWatchlistItemDeletion: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.watchlist_entry_list_item_gap)),
        modifier = modifier
            .padding(dimensionResource(R.dimen.watchlist_entry_list_margin))
    ) {
        items(watchlistItems) { watchlistItem ->
            WatchlistEntry(
                watchlistItem = watchlistItem,
                watchlistEntrySize = watchlistEntrySize,
                onWatchlistItemClicked = onWatchlistItemClicked,
                onWatchlistItemDeletion = onWatchlistItemDeletion,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WatchlistEntryListPreview() {
    CurrencyConverterAppTheme {
        WatchlistEntryList(
            watchlistItems = defaultWatchlistItems,
            watchlistEntrySize = WatchlistEntrySize.DEFAULT,
            onWatchlistItemClicked = { },
            onWatchlistItemDeletion = { }
        )
    }
}