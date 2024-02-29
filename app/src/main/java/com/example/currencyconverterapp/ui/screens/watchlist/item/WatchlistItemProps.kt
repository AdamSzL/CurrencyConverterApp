package com.example.currencyconverterapp.ui.screens.watchlist.item

import androidx.annotation.StringRes
import com.example.currencyconverterapp.data.model.Currency
import com.example.currencyconverterapp.data.model.ExchangeRateRelation
import com.example.currencyconverterapp.data.model.WatchlistItem

class WatchlistItemProps(
    val watchlistItemUiState: WatchlistItemUiState,
    @StringRes val confirmButtonText: Int,
    val onBaseCurrencySelection: (Currency) -> Unit,
    val onTargetCurrencySelection: (Currency) -> Unit,
    val onBaseAndTargetCurrenciesSwap: () -> Unit,
    val onExchangeRateRelationSelection: (ExchangeRateRelation) -> Unit,
    val onTargetValueChange: (Double) -> Unit,
    val onConfirmButtonClicked: (WatchlistItem) -> Unit,
    val onCancelButtonClicked: () -> Unit,
    val onLatestExchangeRateUpdate: () -> Unit,
)
