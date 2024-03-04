package com.example.currencyconverterapp.watchlist.presentation.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun WatchlistNotificationRequestPermissionRationale(
    onNotInterestedButtonClicked: () -> Unit,
    onIAmInButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        NotificationRequestInfo()

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_watchlist),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                contentDescription = null,
                modifier = Modifier.size(192.dp)
            )
        }

        NotificationRequestActions(
            onNotInterestedButtonClicked = onNotInterestedButtonClicked,
            onIAmInButtonClicked = onIAmInButtonClicked,
        )
    }
}

@Preview(showBackground = true)
@PreviewLightDark
@Composable
private fun WatchlistNotificationsScreenPreview() {
    CurrencyConverterAppTheme {
        WatchlistNotificationRequestPermissionRationale(
            onIAmInButtonClicked = { },
            onNotInterestedButtonClicked = { }
        )
    }
}