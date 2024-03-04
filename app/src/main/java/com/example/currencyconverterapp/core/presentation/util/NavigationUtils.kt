package com.example.currencyconverterapp.core.presentation.util

import com.example.currencyconverterapp.core.presentation.navigation.CurrencyConverterScreen
import com.example.currencyconverterapp.core.presentation.navigation.WatchlistSubScreen

object NavigationUtils {
    fun getCurrentConverterWatchlistScreen(route: String?): Pair<CurrencyConverterScreen, WatchlistSubScreen?> {
        if (route == null || !route.contains("Watchlist")) {
            return Pair(CurrencyConverterScreen.valueOf(route ?: CurrencyConverterScreen.Converter.name), null)
        }

        val watchlistSubScreen = if (route == "${WatchlistSubScreen.WatchlistEditItem}/{watchlist_item_id}") {
            WatchlistSubScreen.WatchlistEditItem
        } else {
            WatchlistSubScreen.valueOf(route)
        }
        return Pair(CurrencyConverterScreen.Watchlist, watchlistSubScreen)
    }

}