package com.example.currencyconverterapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.core.presentation.CurrenciesUiState
import com.example.currencyconverterapp.core.presentation.util.FloatingActionButtonType
import com.example.currencyconverterapp.core.presentation.util.WatchlistEntrySize
import com.example.currencyconverterapp.core.presentation.util.WatchlistItemScreenContentType
import com.example.currencyconverterapp.core.presentation.util.WatchlistScreenContentType
import com.example.currencyconverterapp.watchlist.presentation.item.WatchlistItemViewModel
import com.example.currencyconverterapp.watchlist.presentation.list.WatchlistScreenWrapper
import com.example.currencyconverterapp.watchlist.presentation.list.WatchlistViewModel
import com.example.currencyconverterapp.watchlist.presentation.util.constructWatchlistItemProps

@Composable
fun WatchlistItemsRoute(
    watchlistScreenContentType: WatchlistScreenContentType,
    currenciesUiState: CurrenciesUiState,
    fabType: FloatingActionButtonType,
    watchlistEntrySize: WatchlistEntrySize,
    watchlistItemScreenContentType: WatchlistItemScreenContentType,
    screenAdaptiveNavigationWrapperProps: ScreenAdaptiveNavigationWrapperProps,
    onLaunchAppSettingsClick: () -> Unit,
    navigateTo: (String) -> Unit,
) {
    val watchlistViewModel: WatchlistViewModel = hiltViewModel()
    val watchlistItems = watchlistViewModel.watchlistItems.collectAsStateWithLifecycle().value
    var (currencies, watchlistItemProps, selectedItemId, onWatchlistItemUpdate, onBackToAdditionReset) = WatchlistAdaptiveProps()
    if (watchlistScreenContentType == WatchlistScreenContentType.TWO_PANELS) {
        val watchlistItemViewModel: WatchlistItemViewModel = hiltViewModel()
        val watchlistItemUiState
                = watchlistItemViewModel.watchlistItemUiState.collectAsStateWithLifecycle().value
        selectedItemId = watchlistItemUiState.itemId
        currencies = (currenciesUiState as CurrenciesUiState.Success).currencies
        onWatchlistItemUpdate = watchlistItemViewModel::updateSelectedItem
        onBackToAdditionReset = watchlistItemViewModel::resetBackToAddition
        watchlistItemProps = constructWatchlistItemProps(
            watchlistItemViewModel = watchlistItemViewModel,
            watchlistItemTargetValue = watchlistItemViewModel.targetValue,
            watchlistItemUiState = watchlistItemUiState,
            confirmButtonText = if (selectedItemId != null) R.string.update else R.string.add,
            onConfirmButtonClicked = { watchlistItem ->
                if (selectedItemId != null) {
                    watchlistItemViewModel.editWatchlistItem(watchlistItem)
                } else {
                    watchlistItemViewModel.addWatchlistItem(watchlistItem)
                }
            },
            onCancelButtonClicked = {  },
            onLaunchAppSettingsClick = onLaunchAppSettingsClick,
        )
    }
    WatchlistScreenWrapper(
        watchlistItems = watchlistItems,
        watchlistEntrySize = watchlistEntrySize,
        watchlistScreenContentType = watchlistScreenContentType,
        watchlistItemScreenContentType = watchlistItemScreenContentType,
        currencies = currencies,
        watchlistItemProps = watchlistItemProps,
        fabType = fabType,
        screenAdaptiveNavigationWrapperProps = screenAdaptiveNavigationWrapperProps,
        fabAction = {
            if (watchlistScreenContentType == WatchlistScreenContentType.ONE_PANEL) {
                navigateTo(WatchlistSubScreen.WatchlistAddItem.name)
            } else {
                onBackToAdditionReset!!()
            }
        },
        onWatchlistItemClicked = { watchlistItemId ->
            if (watchlistScreenContentType == WatchlistScreenContentType.ONE_PANEL) {
                navigateTo("${WatchlistSubScreen.WatchlistEditItem.name}/${watchlistItemId}")
            } else {
                (onWatchlistItemUpdate!!)(watchlistItemId)
            }
        },
        onWatchlistItemDeletion = { watchlistItemId ->
            if (watchlistScreenContentType == WatchlistScreenContentType.TWO_PANELS
                && selectedItemId == watchlistItemId
            ) {
                onBackToAdditionReset!!()
            }
            watchlistViewModel.removeWatchlistItem(watchlistItemId)
        },
        onAddButtonClicked = {
            navigateTo(WatchlistSubScreen.WatchlistAddItem.name)
        }
    )
}