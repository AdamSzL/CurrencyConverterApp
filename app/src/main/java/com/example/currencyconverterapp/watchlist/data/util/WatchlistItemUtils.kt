package com.example.currencyconverterapp.watchlist.data.util

import com.example.currencyconverterapp.watchlist.data.model.ExchangeRateRelation
import com.example.currencyconverterapp.watchlist.data.model.WatchlistItem

object WatchlistItemUtils {

    fun checkIfWatchlistItemConditionFulfilled(
        watchlistItem: WatchlistItem,
        latestExchangeRate: Double,
    ): Boolean {
        return when (watchlistItem.exchangeRateRelation) {
            ExchangeRateRelation.GREATER_THAN ->
                latestExchangeRate > watchlistItem.targetValue
            ExchangeRateRelation.GREATER_THAN_OR_EQUAL ->
                latestExchangeRate >= watchlistItem.targetValue
            ExchangeRateRelation.LESS_THAN ->
                latestExchangeRate < watchlistItem.targetValue
            ExchangeRateRelation.LESS_THAN_OR_EQUAL ->
                latestExchangeRate <= watchlistItem.targetValue
        }
    }
}