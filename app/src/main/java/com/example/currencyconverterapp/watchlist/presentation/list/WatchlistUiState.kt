package com.example.currencyconverterapp.watchlist.presentation.list

import com.example.currencyconverterapp.watchlist.data.model.WatchlistItem
import com.example.currencyconverterapp.watchlist.presentation.notifications.NotificationsPermissionState

data class WatchlistUiState(
    val watchlistItems: List<WatchlistItem> = emptyList(),
    val notificationsPermissionState: NotificationsPermissionState = NotificationsPermissionState.ASK_FOR_PERMISSION
)
