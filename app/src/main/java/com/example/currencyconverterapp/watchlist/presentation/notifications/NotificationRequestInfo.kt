package com.example.currencyconverterapp.watchlist.presentation.notifications

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme
import com.example.currencyconverterapp.ui.theme.Montserrat

@Composable
fun NotificationRequestInfo(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .padding(16.dp),
    ) {
        Icon(
            imageVector = Icons.Filled.Notifications,
            tint = MaterialTheme.colorScheme.secondary,
            contentDescription = "notifications",
            modifier = Modifier.size(dimensionResource(R.dimen.notifications_icon_size_big))
        )
        Text(
            text = "Get Notified!",
            style = MaterialTheme.typography.displayMedium,
        )
        Text(
            text = "Want to get notified when a currency passes a certain threshold? The watchlist screen will help you do just that. Just turn on notifications and start using it!",
            style = MaterialTheme.typography.bodySmall.copy(fontFamily = Montserrat),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NotificationRequestInfoPreview() {
    CurrencyConverterAppTheme {
        NotificationRequestInfo()
    }
}