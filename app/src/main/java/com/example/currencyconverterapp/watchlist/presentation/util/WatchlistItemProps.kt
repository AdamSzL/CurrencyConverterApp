package com.example.currencyconverterapp.watchlist.presentation.util

import androidx.annotation.StringRes
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.watchlist.data.model.ExchangeRateRelation
import com.example.currencyconverterapp.watchlist.data.model.WatchlistItem
import com.example.currencyconverterapp.watchlist.presentation.item.WatchlistItemUiState
import com.example.currencyconverterapp.watchlist.presentation.item.WatchlistItemViewModel

class WatchlistItemProps(
    val watchlistItemUiState: WatchlistItemUiState,
    val watchlistItemTargetValue: String,
    @StringRes val confirmButtonText: Int,
    val onBaseCurrencySelection: (Currency) -> Unit,
    val onTargetCurrencySelection: (Currency) -> Unit,
    val onBaseAndTargetCurrenciesSwap: () -> Unit,
    val onExchangeRateRelationSelection: (ExchangeRateRelation) -> Unit,
    val onTargetValueChange: (String) -> Unit,
    val onConfirmButtonClicked: (WatchlistItem) -> Unit,
    val onCancelButtonClicked: () -> Unit,
    val onLatestExchangeRateUpdate: () -> Unit,
    val onNotificationsPermissionRejectionStateUpdate: (Boolean) -> Unit,
    val onLaunchAppSettingsClick: () -> Unit,
)

fun constructWatchlistItemProps(
    watchlistItemViewModel: WatchlistItemViewModel,
    watchlistItemUiState: WatchlistItemUiState,
    watchlistItemTargetValue: String,
    @StringRes confirmButtonText: Int,
    onConfirmButtonClicked: (WatchlistItem) -> Unit,
    onCancelButtonClicked: () -> Unit,
    onLaunchAppSettingsClick: () -> Unit,
): WatchlistItemProps {
    return WatchlistItemProps(
        watchlistItemUiState = watchlistItemUiState,
        watchlistItemTargetValue = watchlistItemTargetValue,
        confirmButtonText = confirmButtonText,
        onBaseCurrencySelection = watchlistItemViewModel::selectBaseCurrency,
        onTargetCurrencySelection = watchlistItemViewModel::selectTargetCurrency,
        onBaseAndTargetCurrenciesSwap = watchlistItemViewModel::swapBaseAndTargetCurrencies,
        onExchangeRateRelationSelection = watchlistItemViewModel::selectExchangeRateRelation,
        onTargetValueChange = watchlistItemViewModel::updateTargetValue,
        onConfirmButtonClicked = onConfirmButtonClicked,
        onCancelButtonClicked = onCancelButtonClicked,
        onLatestExchangeRateUpdate = watchlistItemViewModel::restoreToLoadingStateAndFetchExchangeRate,
        onNotificationsPermissionRejectionStateUpdate = watchlistItemViewModel::updateNotificationsPermissionRejectionState,
        onLaunchAppSettingsClick = onLaunchAppSettingsClick,
    )
}
