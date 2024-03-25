package com.example.currencyconverterapp.watchlist.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.data.util.defaultAvailableCurrencies
import com.example.currencyconverterapp.core.data.util.defaultWatchlistItems
import com.example.currencyconverterapp.core.presentation.components.ScreenAdaptiveNavigationWrapper
import com.example.currencyconverterapp.core.presentation.navigation.CurrencyConverterScreen
import com.example.currencyconverterapp.core.presentation.navigation.ScreenAdaptiveNavigationWrapperProps
import com.example.currencyconverterapp.core.presentation.util.CurrencyConverterNavigationType
import com.example.currencyconverterapp.core.presentation.util.FloatingActionButtonType
import com.example.currencyconverterapp.core.presentation.util.WatchlistEntrySize
import com.example.currencyconverterapp.core.presentation.util.WatchlistItemScreenContentType
import com.example.currencyconverterapp.core.presentation.util.WatchlistScreenContentType
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme
import com.example.currencyconverterapp.watchlist.data.model.WatchlistItem
import com.example.currencyconverterapp.watchlist.presentation.item.WatchlistItemScreen
import com.example.currencyconverterapp.watchlist.presentation.item.WatchlistItemUiState
import com.example.currencyconverterapp.watchlist.presentation.item.latest_exchange_rate.LatestExchangeRateUiState
import com.example.currencyconverterapp.watchlist.presentation.util.WatchlistItemProps

@Composable
fun WatchlistScreenWrapper(
    watchlistItems: List<WatchlistItem>,
    watchlistEntrySize: WatchlistEntrySize,
    watchlistScreenContentType: WatchlistScreenContentType,
    watchlistItemScreenContentType: WatchlistItemScreenContentType,
    currencies: List<Currency>?,
    watchlistItemProps: WatchlistItemProps?,
    fabType: FloatingActionButtonType,
    screenAdaptiveNavigationWrapperProps: ScreenAdaptiveNavigationWrapperProps,
    fabAction: () -> Unit,
    onWatchlistItemClicked: (String) -> Unit,
    onWatchlistItemDeletion: (String) -> Unit,
    onAddButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScreenAdaptiveNavigationWrapper(
        screenAdaptiveNavigationWrapperProps = screenAdaptiveNavigationWrapperProps,
        fabAction = fabAction,
        modifier = modifier
    ) {
        Row {
            WatchlistScreen(
                watchlistItems = watchlistItems,
                watchlistEntrySize = watchlistEntrySize,
                fabType = fabType,
                onWatchlistItemClicked = onWatchlistItemClicked,
                onWatchlistItemDeletion = onWatchlistItemDeletion,
                onAddButtonClicked = onAddButtonClicked
            )
            if (watchlistScreenContentType == WatchlistScreenContentType.TWO_PANELS) {
                WatchlistItemScreen(
                    currencies = currencies!!,
                    watchlistItemProps = watchlistItemProps!!,
                    shouldDisplayCancelButton = false,
                    watchlistItemScreenContentType = watchlistItemScreenContentType,
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topStart = dimensionResource(R.dimen.side_panel_rounded_corner),
                                bottomStart = dimensionResource(R.dimen.side_panel_rounded_corner)
                            )
                        )
                        .background(MaterialTheme.colorScheme.inverseOnSurface),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WatchlistScreenWrapperOnePanelPreview() {
    CurrencyConverterAppTheme {
        WatchlistScreenWrapper(
            watchlistItems = defaultWatchlistItems,
            watchlistEntrySize = WatchlistEntrySize.DEFAULT,
            watchlistScreenContentType = WatchlistScreenContentType.ONE_PANEL,
            currencies = defaultAvailableCurrencies,
            watchlistItemProps = WatchlistItemProps(
                watchlistItemUiState = WatchlistItemUiState(
                    latestExchangeRateUiState = LatestExchangeRateUiState.Success(
                        1.23,
                        "2024-02-22T16:51:52Z"
                    )
                ),
                watchlistItemTargetValue = "1.00",
                confirmButtonText = R.string.add,
                onBaseCurrencySelection = { },
                onTargetCurrencySelection = { },
                onBaseAndTargetCurrenciesSwap = { },
                onExchangeRateRelationSelection = { },
                onTargetValueChange = { },
                onConfirmButtonClicked = { },
                onCancelButtonClicked = { },
                onLatestExchangeRateUpdate = { },
                onNotificationsPermissionRejectionStateUpdate = { },
                onLaunchAppSettingsClick = { }
            ),
            watchlistItemScreenContentType = WatchlistItemScreenContentType.SMALL_FONT,
            fabType = FloatingActionButtonType.BOTTOM_RIGHT,
            screenAdaptiveNavigationWrapperProps = ScreenAdaptiveNavigationWrapperProps(
                navigationType = CurrencyConverterNavigationType.BOTTOM_NAVIGATION,
                currentCurrencyConverterScreen = CurrencyConverterScreen.Watchlist,
                navigateTo = { },
                dataHandlerUiState = "Watchlist",
                onRetryAction = { }
            ),
            fabAction = { },
            onWatchlistItemClicked = { },
            onWatchlistItemDeletion = { },
            onAddButtonClicked = { }
        )
    }
}

@Preview(showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240"
)
@Composable
fun WatchlistScreenWrapperTwoPanelsPreview() {
    CurrencyConverterAppTheme {
        WatchlistScreenWrapper(
            watchlistItems = defaultWatchlistItems,
            watchlistEntrySize = WatchlistEntrySize.DEFAULT,
            watchlistScreenContentType = WatchlistScreenContentType.TWO_PANELS,
            currencies = defaultAvailableCurrencies,
            watchlistItemProps = WatchlistItemProps(
                watchlistItemUiState = WatchlistItemUiState(
                    latestExchangeRateUiState = LatestExchangeRateUiState.Success(
                        1.23,
                        "2024-02-22T16:51:52Z"
                    )
                ),
                watchlistItemTargetValue = "1.00",
                confirmButtonText = R.string.add,
                onBaseCurrencySelection = { },
                onTargetCurrencySelection = { },
                onBaseAndTargetCurrenciesSwap = { },
                onExchangeRateRelationSelection = { },
                onTargetValueChange = { },
                onConfirmButtonClicked = { },
                onCancelButtonClicked = { },
                onLatestExchangeRateUpdate = { },
                onNotificationsPermissionRejectionStateUpdate = { },
                onLaunchAppSettingsClick = { }
            ),
            watchlistItemScreenContentType = WatchlistItemScreenContentType.SMALL_FONT,
            fabType = FloatingActionButtonType.BOTTOM_RIGHT,
            screenAdaptiveNavigationWrapperProps = ScreenAdaptiveNavigationWrapperProps(
                navigationType = CurrencyConverterNavigationType.BOTTOM_NAVIGATION,
                currentCurrencyConverterScreen = CurrencyConverterScreen.Watchlist,
                navigateTo = { },
                dataHandlerUiState = "Watchlist",
                onRetryAction = { }
            ),
            fabAction = { },
            onWatchlistItemClicked = { },
            onWatchlistItemDeletion = { },
            onAddButtonClicked = { }
        )
    }
}
