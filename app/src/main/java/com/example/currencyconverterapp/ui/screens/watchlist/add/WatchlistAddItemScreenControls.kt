package com.example.currencyconverterapp.ui.screens.watchlist.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.model.WatchlistItem

@Composable
fun WatchlistAddItemScreenControls(
    watchlistAddItemUiState: WatchlistAddItemUiState,
    onWatchlistItemAddition: (WatchlistItem) -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.watchlist_add_main_margin)),
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.watchlist_add_main_margin))
    ) {
        OutlinedButton(
            onClick = navigateUp,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = stringResource(R.string.cancel))
        }
        Button(
            onClick = {
                onWatchlistItemAddition(
                    WatchlistItem(
                        baseCurrency = watchlistAddItemUiState.baseCurrency,
                        targetCurrency = watchlistAddItemUiState.targetCurrency,
                        targetValue = watchlistAddItemUiState.targetValue,
                    )
                )
                navigateUp()
            },
            modifier = Modifier.weight(1f)
        ) {
            Text(text = stringResource(R.string.add))
        }
    }
}