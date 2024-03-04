package com.example.currencyconverterapp.watchlist.presentation.item

import androidx.annotation.StringRes
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R

@Composable
fun WatchlistItemScreenControls(
    @StringRes confirmButtonText: Int,
    onConfirmButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.watchlist_add_main_margin)),
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.watchlist_add_main_margin))
    ) {
        OutlinedButton(
            onClick = onCancelButtonClicked,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = stringResource(R.string.cancel))
        }
        Button(
            onClick = onConfirmButtonClicked,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = stringResource(confirmButtonText))
        }
    }
}

@Preview
@Composable
private fun WatchlistItemScreenControlsAddPreview() {
    WatchlistItemScreenControls(
        confirmButtonText = R.string.add,
        onConfirmButtonClicked = { },
        onCancelButtonClicked = { },
    )
}

@Preview
@Composable
private fun WatchlistItemScreenControlsUpdatePreview() {
    WatchlistItemScreenControls(
        confirmButtonText = R.string.update,
        onConfirmButtonClicked = { },
        onCancelButtonClicked = { },
    )
}
