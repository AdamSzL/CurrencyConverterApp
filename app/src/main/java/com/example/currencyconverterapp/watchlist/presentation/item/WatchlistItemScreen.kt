package com.example.currencyconverterapp.watchlist.presentation.item

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import com.example.currencyconverterapp.watchlist.presentation.notifications.WatchlistNotificationRequestPermissionRationale
import com.example.currencyconverterapp.watchlist.presentation.util.NotificationUtils.getNotificationsPermissionState
import java.util.UUID

@Composable
fun WatchlistItemScreen(
    currencies: List<Currency>,
    watchlistItemProps: WatchlistItemProps,
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

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
//                notificationsPermissionState = NotificationsPermissionState.PERMISSION_GRANTED
                watchlistItemProps
                    .onConfirmButtonClicked(
                        getWatchlistItemFromUiState(watchlistItemProps.watchlistItemUiState)
                    )
            } else {
                watchlistItemProps.onCancelButtonClicked()
            }
        }
    )

    val onNotificationsPermissionRequest = {
        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    AnimatedContent(
        targetState = shouldShowRationale,
        transitionSpec = {
            fadeIn(
                animationSpec = tween(1000)
            ) togetherWith fadeOut(animationSpec = tween(1000))
        }
    ) { targetState ->
        if (targetState) {
            WatchlistNotificationRequestPermissionRationale(
                onNotInterestedButtonClicked = watchlistItemProps.onCancelButtonClicked,
                onIAmInButtonClicked = onNotificationsPermissionRequest,
                modifier = modifier
            )
        } else {
            WatchlistItemDetails(
                currencies = currencies,
                watchlistItemProps = watchlistItemProps,
                notificationsPermissionState = notificationsPermissionState,
                onRationaleDisplay = {
                    shouldShowRationale = true
                },
                onNotificationsPermissionRequest = onNotificationsPermissionRequest,
                modifier = modifier
            )
        }
    }
}

fun getWatchlistItemFromUiState(
    watchlistItemUiState: WatchlistItemUiState
): WatchlistItem {
    return WatchlistItem(
        id = watchlistItemUiState.itemId ?: UUID.randomUUID().toString(),
        baseCurrency = watchlistItemUiState.baseCurrency,
        targetCurrency = watchlistItemUiState.targetCurrency,
        targetValue = watchlistItemUiState.targetValue,
        exchangeRateRelation = watchlistItemUiState.exchangeRateRelation,
    )
}

@Preview
@PreviewScreenSizes
@Composable
fun WatchlistAddItemScreenPreview() {
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
                confirmButtonText = R.string.add,
                onBaseCurrencySelection = { },
                onTargetCurrencySelection = { },
                onBaseAndTargetCurrenciesSwap = { },
                onExchangeRateRelationSelection = { },
                onTargetValueChange = { },
                onConfirmButtonClicked = { },
                onCancelButtonClicked = { },
                onLatestExchangeRateUpdate = { },
            )
        )
    }
}
