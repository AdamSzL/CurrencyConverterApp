package com.example.currencyconverterapp.core.presentation.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ScopeUpdateScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.core.presentation.CurrenciesUiState
import com.example.currencyconverterapp.core.presentation.components.ScreenAdaptiveNavigationWrapper
import com.example.currencyconverterapp.core.presentation.util.WatchlistItemScreenContentType
import com.example.currencyconverterapp.watchlist.presentation.item.WatchlistItemScreen
import com.example.currencyconverterapp.watchlist.presentation.item.WatchlistItemViewModel
import com.example.currencyconverterapp.watchlist.presentation.util.constructWatchlistItemProps

@Composable
fun WatchlistEditItemRoute(
    screenAdaptiveNavigationWrapperProps: ScreenAdaptiveNavigationWrapperProps,
    watchlistItemScreenContentType: WatchlistItemScreenContentType,
    currenciesUiState: CurrenciesUiState,
    onLaunchAppSettingsClick: () -> Unit,
    navigateUp: () -> Unit,
) {
    val watchlistItemViewModel: WatchlistItemViewModel = hiltViewModel()
    val watchlistItemUiState
            = watchlistItemViewModel.watchlistItemUiState.collectAsStateWithLifecycle().value
    ScreenAdaptiveNavigationWrapper(
        screenAdaptiveNavigationWrapperProps = screenAdaptiveNavigationWrapperProps
    ) {
        Row {
            WatchlistItemScreen(
                currencies = (currenciesUiState as CurrenciesUiState.Success).currencies,
                watchlistItemProps = constructWatchlistItemProps(
                    watchlistItemViewModel = watchlistItemViewModel,
                    watchlistItemUiState = watchlistItemUiState,
                    watchlistItemTargetValue = watchlistItemViewModel.targetValue,
                    confirmButtonText = R.string.update,
                    onConfirmButtonClicked = { watchlistItem ->
                        watchlistItemViewModel.editWatchlistItem(watchlistItem)
                        navigateUp()
                    },
                    onCancelButtonClicked = {
                        navigateUp()
                    },
                    onLaunchAppSettingsClick = onLaunchAppSettingsClick,
                ),
                watchlistItemScreenContentType = watchlistItemScreenContentType,
                shouldDisplayCancelButton = true,
            )
        }
    }
}