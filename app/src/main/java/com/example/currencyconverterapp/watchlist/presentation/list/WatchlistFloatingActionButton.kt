package com.example.currencyconverterapp.watchlist.presentation.list

import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.core.presentation.components.AdaptiveFloatingActionButton
import com.example.currencyconverterapp.core.presentation.util.FabSize

@Composable
fun WatchlistFloatingActionButton(
    size: FabSize,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AdaptiveFloatingActionButton(
        size = size,
        icon = {
            Image(
                painter = painterResource(R.drawable.ic_watchlist),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                contentDescription = stringResource(R.string.watchlist_add)
            )
        },
        text = {
            Text(
                text = stringResource(R.string.watchlist_add),
            )
        },
        onClick = onClick,
        modifier = modifier
    )
}
