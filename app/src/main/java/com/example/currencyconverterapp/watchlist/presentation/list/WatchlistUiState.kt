package com.example.currencyconverterapp.watchlist.presentation.list

import com.example.currencyconverterapp.watchlist.data.model.WatchlistItem

data class WatchlistUiState(
    val watchlistItems: List<WatchlistItem> = emptyList()
)
