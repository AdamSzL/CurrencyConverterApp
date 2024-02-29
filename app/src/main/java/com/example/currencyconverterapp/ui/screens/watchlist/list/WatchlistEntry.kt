package com.example.currencyconverterapp.ui.screens.watchlist.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.data.model.WatchlistItem
import com.example.currencyconverterapp.data.util.defaultWatchlistItems
import com.example.currencyconverterapp.ui.screens.converter.conversion_results.getCurrencyFormat
import com.example.currencyconverterapp.ui.screens.watchlist.WatchlistItemCurrencyFlags
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun WatchlistEntry(
    watchlistItem: WatchlistItem,
    onWatchlistItemClicked: (String) -> Unit,
    onWatchlistItemDeletion: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val baseCurrencyFormat = getCurrencyFormat(watchlistItem.baseCurrency)
    val targetCurrencyFormat = getCurrencyFormat(watchlistItem.targetCurrency)

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = {
            onWatchlistItemClicked(watchlistItem.id)
        },
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 15.dp,
                    topEnd = 30.dp,
                    bottomStart = 30.dp,
                    bottomEnd = 15.dp,
                )
            )
    ) {
        val context = LocalContext.current
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.watchlist_item_small_gap)),
                modifier = Modifier
                    .weight(1f)
                    .padding(dimensionResource(R.dimen.watchlist_item_margin))
            ) {
                WatchlistItemCurrencyFlags(
                    context = context,
                    baseCurrency = watchlistItem.baseCurrency,
                    targetCurrency = watchlistItem.targetCurrency,
                )
                Text(
                    text = stringResource(
                        R.string.notification_when,
                        baseCurrencyFormat.format(1),
                        watchlistItem.exchangeRateRelation.label.lowercase(),
                        targetCurrencyFormat.format(watchlistItem.targetValue)
                    )
                )
            }
            FilledIconButton(
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                ),
                onClick = {
                    onWatchlistItemDeletion(watchlistItem.id)
                },
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.watchlist_remove_icon_margin))
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_watchlist_remove),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                    contentDescription = stringResource(R.string.watchlist_add)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@PreviewLightDark
@Composable
fun WatchlistEntryPreview() {
    CurrencyConverterAppTheme {
        WatchlistEntry(
            watchlistItem = defaultWatchlistItems.first(),
            onWatchlistItemClicked = { },
            onWatchlistItemDeletion = { }
        )
    }
}