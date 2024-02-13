package com.example.currencyconverterapp.ui.screens.converter

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.currencyconverterapp.R

@Composable
fun CurrenciesErrorScreen(
    @StringRes errorMessage: Int,
    onErrorRetryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.ic_network_off),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
            contentDescription = stringResource(R.string.network_off)
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.error_screen_column_gap)))
        Text(
            text = stringResource(errorMessage),
            style = MaterialTheme.typography.displaySmall,
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.error_screen_column_gap)))
        Button(
           onClick = onErrorRetryAction,
        ) {
            Text(
                text = stringResource(R.string.try_again)
            )
        }
    }
}