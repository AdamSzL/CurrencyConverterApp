package com.example.currencyconverterapp.watchlist.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WatchlistData(
    val watchlistItems: List<WatchlistItem> = emptyList()
)
