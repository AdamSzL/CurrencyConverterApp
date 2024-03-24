package com.example.currencyconverterapp.watchlist.presentation.item

import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.data.util.defaultBaseCurrency
import com.example.currencyconverterapp.core.data.util.defaultTargetCurrency
import com.example.currencyconverterapp.watchlist.data.model.ExchangeRateRelation
import com.example.currencyconverterapp.watchlist.presentation.item.latest_exchange_rate.LatestExchangeRateUiState

data class WatchlistItemUiState(
    val itemId: String? = null,
    val baseCurrency: Currency = defaultBaseCurrency,
    val targetCurrency: Currency = defaultTargetCurrency,
    val exchangeRateRelation: ExchangeRateRelation = ExchangeRateRelation.LESS_THAN,
    val latestExchangeRateUiState: LatestExchangeRateUiState = LatestExchangeRateUiState.Loading,
    val isNotificationsPermissionPermanentlyRejected: Boolean = false,
)