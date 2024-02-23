package com.example.currencyconverterapp.ui.screens.watchlist.item

import com.example.currencyconverterapp.data.defaultBaseCurrency
import com.example.currencyconverterapp.data.defaultTargetCurrency
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.ExchangeRateRelation

data class WatchlistItemUiState(
    val itemId: String? = null,
    val baseCurrency: Currency = defaultBaseCurrency,
    val targetCurrency: Currency = defaultTargetCurrency,
    val exchangeRateRelation: ExchangeRateRelation = ExchangeRateRelation.LESS_THAN,
    val targetValue: Double = 1.15,
    val latestExchangeRateUiState: LatestExchangeRateUiState = LatestExchangeRateUiState.Loading
)