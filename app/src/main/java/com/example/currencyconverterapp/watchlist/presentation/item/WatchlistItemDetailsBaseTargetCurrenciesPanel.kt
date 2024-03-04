package com.example.currencyconverterapp.watchlist.presentation.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.converter.presentation.currencies_dropdown.CurrenciesDropdownMenu
import com.example.currencyconverterapp.core.data.model.Currency

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
            CurrenciesDropdownMenu(
                currencies = currencies.filter { it.code != watchlistItemUiState.targetCurrency.code },
                textLabel = null,
                selectedCurrency = watchlistItemUiState.baseCurrency,
                onCurrencySelection = onBaseCurrencySelection,
            )
        }

        FilledIconButton(
            onClick = onBaseAndTargetCurrenciesSwap,
            modifier = modifier
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

        Spacer(modifier = modifier.height(dimensionResource(R.dimen.watchlist_add_main_margin)))

        EntryItemRow(
            entryLabel = R.string.target_currency,
            modifier = modifier
        ) {
            CurrenciesDropdownMenu(
                currencies = currencies.filter { it.code != watchlistItemUiState.baseCurrency.code },
                textLabel = null,
                selectedCurrency = watchlistItemUiState.targetCurrency,
                onCurrencySelection = onTargetCurrencySelection,
            )
        }
    }
}