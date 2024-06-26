package com.example.currencyconverterapp.watchlist.presentation.item

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.core.presentation.util.WatchlistItemScreenContentType

@Composable
fun EntryItemRow(
    @StringRes entryLabel: Int,
    watchlistItemScreenContentType: WatchlistItemScreenContentType,
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
            style = if (watchlistItemScreenContentType == WatchlistItemScreenContentType.SMALL_FONT) {
                MaterialTheme.typography.displaySmall
            } else {
                MaterialTheme.typography.displayMedium
            }
        )
        content()
    }
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.watchlist_add_main_margin)))
}
