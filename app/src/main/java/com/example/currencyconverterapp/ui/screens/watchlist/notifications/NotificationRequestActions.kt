package com.example.currencyconverterapp.ui.screens.watchlist.notifications

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun NotificationRequestActions(
    onNotInterestedButtonClicked: () -> Unit,
    onIAmInButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedButton(
            onClick = onNotInterestedButtonClicked,
        ) {
            Text(
                text = "I'm not interested"
            )
        }

        Button(
            onClick = onIAmInButtonClicked,
        ) {
            Text(
                text = "I'm in!"
            )
        }
    }
}

@Preview
@Composable
private fun NotificationRequestActionsPreview() {
    CurrencyConverterAppTheme {
        NotificationRequestActions(
            onNotInterestedButtonClicked = { },
            onIAmInButtonClicked = { }
        )
    }
}