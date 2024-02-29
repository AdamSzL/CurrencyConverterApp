package com.example.currencyconverterapp.ui.screens.watchlist.item

import com.example.currencyconverterapp.data.model.Currency
import com.example.currencyconverterapp.data.model.ExchangeRateRelation
import com.example.currencyconverterapp.data.util.defaultBaseCurrency
import com.example.currencyconverterapp.data.util.defaultTargetCurrency

data class WatchlistItemUiState(
    val itemId: String? = null,
    val baseCurrency: Currency = defaultBaseCurrency,
    val targetCurrency: Currency = defaultTargetCurrency,
    val exchangeRateRelation: ExchangeRateRelation = ExchangeRateRelation.LESS_THAN,
    val targetValue: Double = 1.15,
    val latestExchangeRateUiState: LatestExchangeRateUiState = LatestExchangeRateUiState.Loading
)