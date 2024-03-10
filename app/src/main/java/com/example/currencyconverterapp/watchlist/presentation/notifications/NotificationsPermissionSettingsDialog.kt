package com.example.currencyconverterapp.watchlist.presentation.notifications

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun NotificationsPermissionSettingsDialog(
    onLaunchAppSettingsClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(R.dimen.notifications_icon_size_small))
            )
        },
        title = {
            Text(
                text = stringResource(R.string.notifications_dialog_title),
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
            )
        },
        text = {
            Text(
                text = stringResource(R.string.notifications_dialog_text),
            )
        },
        onDismissRequest = onCancelClicked,
        confirmButton = {
            TextButton(
                onClick = {
                    onCancelClicked()
                    onLaunchAppSettingsClicked()
                },
            ) {
                Text(
                    text = stringResource(R.string.launch_app_settings)
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCancelClicked,
            ) {
                Text(
                    text = stringResource(R.string.cancel)
                )
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun NotificationsPermissionSettingsDialogPreview() {
    CurrencyConverterAppTheme {
        NotificationsPermissionSettingsDialog(
            onLaunchAppSettingsClicked = { },
            onCancelClicked = { }
        )
    }
}