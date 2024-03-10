package com.example.currencyconverterapp.watchlist.presentation.item

import androidx.annotation.StringRes
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.watchlist.data.model.ExchangeRateRelation
import com.example.currencyconverterapp.watchlist.data.model.WatchlistItem

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
    val onNotificationsPermissionRejectionStateUpdate: (Boolean) -> Unit,
    val onLaunchAppSettingsClick: () -> Unit,
)
