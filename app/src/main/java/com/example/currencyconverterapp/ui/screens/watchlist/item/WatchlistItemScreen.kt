package com.example.currencyconverterapp.ui.screens.watchlist.item

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.data.model.Currency
import com.example.currencyconverterapp.data.model.WatchlistItem
import com.example.currencyconverterapp.data.util.defaultAvailableCurrencies
import com.example.currencyconverterapp.ui.screens.watchlist.notifications.NotificationsPermissionState
import com.example.currencyconverterapp.ui.screens.watchlist.notifications.WatchlistNotificationRequestPermissionRationale
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme
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
    Log.d("XXX", shouldShowRationale.toString())
    var notificationsPermissionState by remember {
        mutableStateOf(
            getNotificationsPermissionState(context)
        )
    }
    Log.d("XXX", notificationsPermissionState.toString())

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

@Composable
fun EntryItemRow(
    @StringRes entryLabel: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(entryLabel),
            style = MaterialTheme.typography.displaySmall
        )
        content()
    }
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.watchlist_add_main_margin)))
}

private fun getNotificationsPermissionState(
    context: Context
): NotificationsPermissionState {
    return when {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED -> {
            NotificationsPermissionState.PERMISSION_GRANTED
        }
        ActivityCompat.shouldShowRequestPermissionRationale(
            context as Activity,
            Manifest.permission.POST_NOTIFICATIONS
        ) -> {
            NotificationsPermissionState.SHOW_RATIONALE
        }
        else -> {
            NotificationsPermissionState.ASK_FOR_PERMISSION
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
