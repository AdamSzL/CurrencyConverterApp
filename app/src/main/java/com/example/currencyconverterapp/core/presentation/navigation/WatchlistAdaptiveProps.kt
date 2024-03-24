package com.example.currencyconverterapp.core.presentation.navigation

import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.watchlist.presentation.util.WatchlistItemProps

class WatchlistAdaptiveProps(
    private var currencies: List<Currency>? = null,
    private var watchlistItemProps: WatchlistItemProps? = null,
    private var selectedItemId: String? = null,
    private var onWatchlistItemUpdate: ((String) -> Unit)? = null,
    private var onBackToAdditionReset: (() -> Unit)? = null
) {

    operator fun component1(): List<Currency>? {
        return currencies
    }

    operator fun component2(): WatchlistItemProps? {
        return watchlistItemProps
    }

    operator fun component3(): String? {
        return selectedItemId
    }

    operator fun component4(): ((String) -> Unit)? {
        return onWatchlistItemUpdate
    }

    operator fun component5(): (() -> Unit)? {
        return onBackToAdditionReset
    }

}