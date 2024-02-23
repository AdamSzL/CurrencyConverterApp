package com.example.currencyconverterapp.ui.screens.watchlist.list

import com.example.currencyconverterapp.model.WatchlistItem

data class WatchlistUiState(
    val watchlistItems: List<WatchlistItem> = emptyList()
)
