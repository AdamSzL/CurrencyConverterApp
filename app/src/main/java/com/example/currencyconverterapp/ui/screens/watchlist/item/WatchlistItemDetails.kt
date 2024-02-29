package com.example.currencyconverterapp.ui.screens.watchlist.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.data.model.Currency
import com.example.currencyconverterapp.ui.screens.converter.base_controller.CurrencyValueTextField
import com.example.currencyconverterapp.ui.screens.converter.currencies_dropdown.CurrenciesDropdownMenu
import com.example.currencyconverterapp.ui.screens.watchlist.notifications.NotificationsPermissionState

@Composable
fun WatchlistItemDetails(
    currencies: List<Currency>,
    watchlistItemProps: WatchlistItemProps,
    notificationsPermissionState: NotificationsPermissionState,
    onRationaleDisplay: () -> Unit,
    onNotificationsPermissionRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        with(watchlistItemProps) {
            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(dimensionResource(R.dimen.watchlist_add_main_margin))
            ) {

                EntryItemRow(
                    entryLabel = R.string.base_currency
                ) {
                    CurrenciesDropdownMenu(
                        currencies = currencies.filter { it.code != watchlistItemUiState.targetCurrency.code },
                        textLabel = null,
                        selectedCurrency = watchlistItemUiState.baseCurrency,
                        onCurrencySelection = onBaseCurrencySelection,
                    )
                }

                FilledIconButton(
                    onClick = onBaseAndTargetCurrenciesSwap,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .rotate(90f)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_swap),
                        contentDescription = stringResource(R.string.swap_currencies),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.swap_icon_margin))
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.watchlist_add_main_margin)))

                EntryItemRow(
                    entryLabel = R.string.target_currency
                ) {
                    CurrenciesDropdownMenu(
                        currencies = currencies.filter { it.code != watchlistItemUiState.baseCurrency.code },
                        textLabel = null,
                        selectedCurrency = watchlistItemUiState.targetCurrency,
                        onCurrencySelection = onTargetCurrencySelection,
                    )
                }

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
                    when (notificationsPermissionState) {
                        NotificationsPermissionState.PERMISSION_GRANTED -> {
                            onConfirmButtonClicked(getWatchlistItemFromUiState(watchlistItemUiState))
                        }
                        NotificationsPermissionState.ASK_FOR_PERMISSION -> {
                            onNotificationsPermissionRequest()
                        }
                        NotificationsPermissionState.SHOW_RATIONALE -> {
                            onRationaleDisplay()
                        }
                    }
                },
            )
        }
    }
}