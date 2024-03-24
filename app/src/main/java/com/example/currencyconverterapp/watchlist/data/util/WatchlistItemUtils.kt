package com.example.currencyconverterapp.watchlist.data.util

import com.example.currencyconverterapp.watchlist.data.model.ExchangeRateRelation
import com.example.currencyconverterapp.watchlist.data.model.WatchlistItem

object WatchlistItemUtils {

    fun checkIfWatchlistItemConditionFulfilled(
        watchlistItem: WatchlistItem,
        latestExchangeRate: Double,
    ): Boolean {
        val targetValue = watchlistItem.targetValue.toDouble()
        return when (watchlistItem.exchangeRateRelation) {
            ExchangeRateRelation.GREATER_THAN ->
                latestExchangeRate > targetValue
            ExchangeRateRelation.GREATER_THAN_OR_EQUAL ->
                latestExchangeRate >= targetValue
            ExchangeRateRelation.LESS_THAN ->
                latestExchangeRate < targetValue
            ExchangeRateRelation.LESS_THAN_OR_EQUAL ->
                latestExchangeRate <= targetValue
        }
    }
}