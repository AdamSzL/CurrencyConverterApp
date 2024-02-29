package com.example.currencyconverterapp.ui.screens.watchlist.list

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.data.model.WatchlistItem
import com.example.currencyconverterapp.data.util.defaultWatchlistItems

@Composable
fun WatchlistScreen(
    watchlistItems: List<WatchlistItem>,
    onWatchlistItemClicked: (String) -> Unit,
    onWatchlistItemDeletion: (String) -> Unit,
    onAddButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.d("XXX", watchlistItems.toString())
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddButtonClicked
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_watchlist),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                    contentDescription = stringResource(R.string.watchlist_add)
                )
            }
        },
        modifier = modifier
    ) { paddingValues ->
        WatchlistEntryList(
            watchlistItems = watchlistItems,
            onWatchlistItemClicked = onWatchlistItemClicked,
            onWatchlistItemDeletion = onWatchlistItemDeletion,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Preview
@PreviewScreenSizes
@Composable
fun WatchlistScreenPreview() {
    WatchlistScreen(
        watchlistItems = defaultWatchlistItems,
        onWatchlistItemClicked = { },
        onWatchlistItemDeletion = { },
        onAddButtonClicked = { },
    )
}
