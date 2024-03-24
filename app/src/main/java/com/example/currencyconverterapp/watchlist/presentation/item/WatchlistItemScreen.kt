package com.example.currencyconverterapp.watchlist.presentation.item

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.data.util.defaultAvailableCurrencies
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme
import com.example.currencyconverterapp.watchlist.data.model.WatchlistItem
import com.example.currencyconverterapp.watchlist.presentation.item.latest_exchange_rate.LatestExchangeRateUiState
import com.example.currencyconverterapp.watchlist.presentation.notifications.NotificationsPermissionSettingsDialog
import com.example.currencyconverterapp.watchlist.presentation.notifications.NotificationsPermissionState
import com.example.currencyconverterapp.watchlist.presentation.notifications.WatchlistNotificationRequestPermissionRationale
import com.example.currencyconverterapp.watchlist.presentation.util.NotificationUtils.getNotificationsPermissionState
import com.example.currencyconverterapp.watchlist.presentation.util.WatchlistItemProps
import com.example.currencyconverterapp.watchlist.presentation.util.WatchlistItemState
import java.util.UUID

@Composable
fun RowScope.WatchlistItemScreen(
    currencies: List<Currency>,
    watchlistItemProps: WatchlistItemProps,
    shouldDisplayCancelButton: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var shouldShowRationale by remember {
        mutableStateOf(false)
    }
    var notificationsPermissionState by remember {
        mutableStateOf(
            getNotificationsPermissionState(context)
        )
    }
    var shouldDisplayPermissionSettingsDialog by remember {
        mutableStateOf(false)
    }
    val watchlistItemState = remember(notificationsPermissionState, shouldDisplayPermissionSettingsDialog, shouldShowRationale) {
        if (notificationsPermissionState == NotificationsPermissionState.PERMISSION_GRANTED) {
            WatchlistItemState.SHOW_ITEM_DETAILS
        } else if (shouldDisplayPermissionSettingsDialog) {
            WatchlistItemState.SHOW_DIALOG
        } else if (shouldShowRationale) {
            WatchlistItemState.SHOW_RATIONALE
        } else {
            WatchlistItemState.SHOW_ITEM_DETAILS
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            notificationsPermissionState = getNotificationsPermissionState(context)
            if (isGranted) {
                watchlistItemProps.onNotificationsPermissionRejectionStateUpdate(false)
                watchlistItemProps
                    .onConfirmButtonClicked(
                        getWatchlistItemFromUiState(
                            watchlistItemProps.watchlistItemUiState,
                            watchlistItemProps.watchlistItemTargetValue
                        )
                    )
            } else {
                if (notificationsPermissionState == NotificationsPermissionState.ASK_FOR_PERMISSION) {
                    watchlistItemProps.onNotificationsPermissionRejectionStateUpdate(true)
                    shouldShowRationale = false
                }
            }
        }
    )

    val onNotificationsPermissionRequest = {
        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    AnimatedContent(
        targetState = watchlistItemState,
        modifier = modifier
            .weight(1f)
    ) { targetState ->
        when (targetState) {
            WatchlistItemState.SHOW_DIALOG -> {
                NotificationsPermissionSettingsDialog(
                    onLaunchAppSettingsClicked = watchlistItemProps.onLaunchAppSettingsClick,
                    onCancelClicked = {
                        shouldDisplayPermissionSettingsDialog = false
                    },
                )
            }
            WatchlistItemState.SHOW_RATIONALE -> {
                WatchlistNotificationRequestPermissionRationale(
                    onNotInterestedButtonClicked = {
                        watchlistItemProps.onNotificationsPermissionRejectionStateUpdate(true)
                        shouldShowRationale = false
                    },
                    onIAmInButtonClicked = onNotificationsPermissionRequest,
                )
            }
            WatchlistItemState.SHOW_ITEM_DETAILS -> {
                WatchlistItemDetails(
                    currencies = currencies,
                    watchlistItemProps = watchlistItemProps,
                    shouldDisplayCancelButton = shouldDisplayCancelButton,
                    onRationaleDisplay = {
                        shouldShowRationale = true
                    },
                    onNotificationsPermissionSettingsDialogDisplay = {
                        shouldDisplayPermissionSettingsDialog = true
                    },
                    onNotificationsPermissionRequest = onNotificationsPermissionRequest,
                )
            }
        }
    }
}

fun getWatchlistItemFromUiState(
    watchlistItemUiState: WatchlistItemUiState,
    watchlistItemTargetValue: String,
): WatchlistItem {
    return WatchlistItem(
        id = watchlistItemUiState.itemId ?: UUID.randomUUID().toString(),
        baseCurrency = watchlistItemUiState.baseCurrency,
        targetCurrency = watchlistItemUiState.targetCurrency,
        targetValue = watchlistItemTargetValue,
        exchangeRateRelation = watchlistItemUiState.exchangeRateRelation,
    )
}

@Preview
@PreviewScreenSizes
@Composable
fun RowScope.WatchlistAddItemScreenPreview() {
    CurrencyConverterAppTheme {
        WatchlistItemScreen(
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
            shouldDisplayCancelButton = true,
        )
    }
}
