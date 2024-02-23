package com.example.currencyconverterapp.ui.screens.watchlist.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.data.defaultWatchlistItems
import com.example.currencyconverterapp.model.WatchlistItem
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun WatchlistEntryList(
    watchlistItems: List<WatchlistItem>,
    onWatchlistItemClicked: (String) -> Unit,
    onWatchlistItemDeletion: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.watchlist_entry_list_item_gap)),
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.watchlist_entry_list_margin))
    ) {
        items(watchlistItems) { watchlistItem ->
            WatchlistEntry(
                watchlistItem = watchlistItem,
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
            onWatchlistItemClicked = { },
            onWatchlistItemDeletion = { }
        )
    }
}