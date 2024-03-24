package com.example.currencyconverterapp.watchlist.presentation.list

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.example.currencyconverterapp.core.data.util.defaultWatchlistItems
import com.example.currencyconverterapp.core.presentation.util.FabSize
import com.example.currencyconverterapp.core.presentation.util.FloatingActionButtonType
import com.example.currencyconverterapp.core.presentation.util.WatchlistEntrySize
import com.example.currencyconverterapp.watchlist.data.model.WatchlistItem

@Composable
fun RowScope.WatchlistScreen(
    watchlistItems: List<WatchlistItem>,
    watchlistEntrySize: WatchlistEntrySize,
    fabType: FloatingActionButtonType,
    onWatchlistItemClicked: (String) -> Unit,
    onWatchlistItemDeletion: (String) -> Unit,
    onAddButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            if (watchlistItems.size < 5 && fabType == FloatingActionButtonType.BOTTOM_RIGHT) {
                WatchlistFloatingActionButton(
                    size = FabSize.SMALL,
                    onClick = onAddButtonClicked
                )
            }
        },
        modifier = modifier
            .weight(1f)
    ) { paddingValues ->
        WatchlistEntryList(
            watchlistItems = watchlistItems,
            watchlistEntrySize = watchlistEntrySize,
            onWatchlistItemClicked = onWatchlistItemClicked,
            onWatchlistItemDeletion = onWatchlistItemDeletion,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Preview
@PreviewScreenSizes
@Composable
fun RowScope.WatchlistScreenPreview() {
    WatchlistScreen(
        watchlistItems = defaultWatchlistItems,
        watchlistEntrySize = WatchlistEntrySize.DEFAULT,
        fabType = FloatingActionButtonType.BOTTOM_RIGHT,
        onWatchlistItemClicked = { },
        onWatchlistItemDeletion = { },
        onAddButtonClicked = { },
    )
}
