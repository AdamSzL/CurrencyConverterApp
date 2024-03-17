package com.example.currencyconverterapp.watchlist.presentation.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.converter.presentation.base_controller.CurrencyValueTextField
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.watchlist.presentation.item.latest_exchange_rate.LatestExchangeRatePanelStateHandler
import com.example.currencyconverterapp.watchlist.presentation.notifications.NotificationsPermissionState
import com.example.currencyconverterapp.watchlist.presentation.util.NotificationUtils.getNotificationsPermissionState
import com.example.currencyconverterapp.watchlist.presentation.util.WatchlistItemProps

@Composable
fun WatchlistItemDetails(
    currencies: List<Currency>,
    watchlistItemProps: WatchlistItemProps,
    onRationaleDisplay: () -> Unit,
    onNotificationsPermissionSettingsDialogDisplay: () -> Unit,
    onNotificationsPermissionRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        with(watchlistItemProps) {
            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(dimensionResource(R.dimen.watchlist_add_main_margin))
            ) {

                WatchlistItemDetailsBaseTargetCurrenciesPanel(
                    currencies = currencies,
                    watchlistItemProps = watchlistItemProps
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.watchlist_add_item_big_gap)))

                Text(
                    text = stringResource(R.string.notify_me, watchlistItemUiState.baseCurrency.code),
                    style = MaterialTheme.typography.displaySmall,
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.watchlist_add_main_margin)))

                Row {
                    ExchangeRateRelationDropdownMenu(
                        watchlistAddItemUiState = watchlistItemUiState,
                        onExchangeRateRelationSelection = onExchangeRateRelationSelection,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.watchlist_add_main_margin)))

                    CurrencyValueTextField(
                        currency = watchlistItemUiState.targetCurrency,
                        currencyValue = watchlistItemUiState.targetValue,
                        onValueChange = onTargetValueChange,
                        shouldShowLabel = false,
                        modifier = Modifier.weight(1f)
                    )
                }

                LatestExchangeRatePanelStateHandler(
                    baseCurrency = watchlistItemUiState.baseCurrency,
                    targetCurrency = watchlistItemUiState.targetCurrency,
                    latestExchangeRateUiState = watchlistItemUiState.latestExchangeRateUiState,
                    onLatestExchangeRateUpdate = onLatestExchangeRateUpdate,
                )
            }

            WatchlistItemScreenControls(
                confirmButtonText = confirmButtonText,
                onCancelButtonClicked = onCancelButtonClicked,
                onConfirmButtonClicked = {
                    val newNotificationsPermissionState = getNotificationsPermissionState(context)
                    when (newNotificationsPermissionState) {
                        NotificationsPermissionState.PERMISSION_GRANTED -> {
                            onConfirmButtonClicked(getWatchlistItemFromUiState(watchlistItemUiState))
                        }
                        NotificationsPermissionState.ASK_FOR_PERMISSION -> {
                            if (watchlistItemProps.watchlistItemUiState.isNotificationsPermissionPermanentlyRejected) {
                                onNotificationsPermissionSettingsDialogDisplay()
                            } else {
                                onNotificationsPermissionRequest()
                            }
                        }
                        NotificationsPermissionState.SHOW_RATIONALE -> {
                            if (watchlistItemProps.watchlistItemUiState.isNotificationsPermissionPermanentlyRejected) {
                                onNotificationsPermissionSettingsDialogDisplay()
                            } else {
                                onRationaleDisplay()
                            }
                        }
                    }
                },
            )
        }
    }
}