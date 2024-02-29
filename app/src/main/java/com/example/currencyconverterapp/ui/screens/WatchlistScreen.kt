package com.example.currencyconverterapp.ui.screens

import androidx.annotation.StringRes
import com.example.currencyconverterapp.R

enum class WatchlistSubScreen(
    val route: String,
    @StringRes val title: Int
) {
    WatchlistItems(
        route = "watchlist_list",
        title = R.string.watchlist,
    ),
    WatchlistAddItem(
        route = "watchlist_add",
        title = R.string.watchlist_add,
    ),
    WatchlistEditItem(
        route = "watchlist_edit",
        title = R.string.watchlist_edit,
    )
}
