package com.example.currencyconverterapp.watchlist.presentation.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.converter.presentation.currencies_dropdown.CurrenciesDropdownMenu
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.data.util.defaultAvailableCurrencies
import com.example.currencyconverterapp.core.presentation.components.SwapButton
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme
import com.example.currencyconverterapp.watchlist.presentation.util.WatchlistItemProps

@Composable
fun ColumnScope.WatchlistItemDetailsBaseTargetCurrenciesPanel(
    currencies: List<Currency>,
    watchlistItemProps: WatchlistItemProps,
    modifier: Modifier = Modifier
) {
    with(watchlistItemProps) {
        EntryItemRow(
            entryLabel = R.string.base_currency,
            modifier = modifier
        ) {

            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.watchlist_item_small_gap)))

            CurrenciesDropdownMenu(
                currencies = currencies.filter { it.code != watchlistItemUiState.targetCurrency.code },
                textLabel = null,
                selectedCurrency = watchlistItemUiState.baseCurrency,
                onCurrencySelection = onBaseCurrencySelection,
            )
        }

        SwapButton(
            contentDescription = R.string.swap_currencies,
            onClick = onBaseAndTargetCurrenciesSwap,
            modifier = modifier
                .align(Alignment.CenterHorizontally),
            rotation = 90.0f,
        )

        Spacer(modifier = modifier.height(dimensionResource(R.dimen.watchlist_add_main_margin)))

        EntryItemRow(
            entryLabel = R.string.target_currency,
            modifier = modifier
        ) {

            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.watchlist_item_small_gap)))

            CurrenciesDropdownMenu(
                currencies = currencies.filter { it.code != watchlistItemUiState.baseCurrency.code },
                textLabel = null,
                selectedCurrency = watchlistItemUiState.targetCurrency,
                onCurrencySelection = onTargetCurrencySelection,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WatchlistDetailsBaseTargetCurrenciesPanelPreview() {
    CurrencyConverterAppTheme {
        Column {
            WatchlistItemDetailsBaseTargetCurrenciesPanel(
                currencies = defaultAvailableCurrencies,
                watchlistItemProps = WatchlistItemProps(
                    watchlistItemUiState = WatchlistItemUiState(),
                    watchlistItemTargetValue = "1.00",
                    confirmButtonText = R.string.update,
                    onBaseAndTargetCurrenciesSwap = { },
                    onTargetCurrencySelection = { },
                    onBaseCurrencySelection = { },
                    onLaunchAppSettingsClick = { },
                    onNotificationsPermissionRejectionStateUpdate = { },
                    onCancelButtonClicked = { },
                    onConfirmButtonClicked = { },
                    onLatestExchangeRateUpdate = { },
                    onTargetValueChange = { },
                    onExchangeRateRelationSelection = { }
                ),
            )
        }
    }
}
