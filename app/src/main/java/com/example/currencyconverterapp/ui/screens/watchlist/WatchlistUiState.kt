package com.example.currencyconverterapp.ui.screens.watchlist

import com.example.currencyconverterapp.model.WatchlistItem

data class WatchlistUiState(
    val watchlistItems: List<WatchlistItem> = emptyList()
)
