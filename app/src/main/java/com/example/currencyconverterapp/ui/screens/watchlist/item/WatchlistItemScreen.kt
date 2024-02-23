package com.example.currencyconverterapp.ui.screens.watchlist.item

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.data.defaultAvailableCurrencies
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.ExchangeRateRelation
import com.example.currencyconverterapp.model.WatchlistItem
import com.example.currencyconverterapp.ui.screens.converter.base_controller.CurrencyValueTextField
import com.example.currencyconverterapp.ui.screens.converter.currencies_dropdown.CurrenciesDropdownMenu
import com.example.currencyconverterapp.ui.screens.watchlist.item.ExchangeRateRelationDropdownMenu
import com.example.currencyconverterapp.ui.screens.watchlist.item.LatestExchangeRatePanelStateHandler
import com.example.currencyconverterapp.ui.screens.watchlist.item.WatchlistItemUiState
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun WatchlistItemScreen(
    currencies: List<Currency>,
    watchlistItemUiState: WatchlistItemUiState,
    @StringRes confirmButtonText: Int,
    onBaseCurrencySelection: (Currency) -> Unit,
    onTargetCurrencySelection: (Currency) -> Unit,
    onBaseAndTargetCurrenciesSwap: () -> Unit,
    onExchangeRateRelationSelection: (ExchangeRateRelation) -> Unit,
    onTargetValueChange: (Double) -> Unit,
    onConfirmButtonClicked: (WatchlistItem) -> Unit,
    onCancelButtonClicked: () -> Unit,
    onLatestExchangeRateUpdate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        with(watchlistItemUiState) {
            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(dimensionResource(R.dimen.watchlist_add_main_margin))
            ) {

                EntryItemRow(
                    entryLabel = R.string.base_currency
                ) {
                    CurrenciesDropdownMenu(
                        currencies = currencies.filter { it.code != targetCurrency.code },
                        textLabel = null,
                        selectedCurrency = baseCurrency,
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
                        currencies = currencies.filter { it.code != baseCurrency.code },
                        textLabel = null,
                        selectedCurrency = targetCurrency,
                        onCurrencySelection = onTargetCurrencySelection,
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.watchlist_add_item_big_gap)))

                Text(
                    text = stringResource(R.string.notify_me, baseCurrency.code),
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
                        currency = targetCurrency,
                        currencyValue = targetValue,
                        onValueChange = onTargetValueChange,
                        shouldShowLabel = false,
                        modifier = Modifier.weight(1f)
                    )
                }

                LatestExchangeRatePanelStateHandler(
                    baseCurrency = baseCurrency,
                    targetCurrency = targetCurrency,
                    latestExchangeRateUiState = latestExchangeRateUiState,
                    onLatestExchangeRateUpdate = onLatestExchangeRateUpdate,
                )
            }

            WatchlistItemScreenControls(
                watchlistItemUiState = watchlistItemUiState,
                confirmButtonText = confirmButtonText,
                onCancelButtonClicked = onCancelButtonClicked,
                onConfirmButtonClicked = onConfirmButtonClicked,
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

@Preview
@PreviewScreenSizes
@Composable
fun WatchlistAddItemScreenPreview() {
    CurrencyConverterAppTheme {
        WatchlistItemScreen(
            currencies = defaultAvailableCurrencies,
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
    }
}
