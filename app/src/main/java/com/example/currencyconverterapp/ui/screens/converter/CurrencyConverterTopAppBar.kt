package com.example.currencyconverterapp.ui.screens.converter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.ui.CurrencyConverterScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterTopAppBar(
    currentScreen: CurrencyConverterScreen,
    isSelectionModeEnabled: Boolean,
    onSelectionModeToggle: () -> Unit,
    onSelectedTargetCurrenciesDeletion: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Box(
                modifier = modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(currentScreen.title),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.align(Alignment.CenterStart),
                )
                if (currentScreen.route == CurrencyConverterScreen.Converter.route && isSelectionModeEnabled) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.app_bar_icons_gap)),
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = dimensionResource(R.dimen.app_bar_icons_gap)),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(R.string.close),
                            modifier = Modifier
                                .clickable {
                                    onSelectionModeToggle()
                                }
                                .size(dimensionResource(R.dimen.top_bar_icon_size))
                        )
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(R.string.delete),
                            modifier = Modifier
                                .clickable {
                                    onSelectedTargetCurrenciesDeletion()
                                    onSelectionModeToggle()
                                }
                                .size(dimensionResource(R.dimen.top_bar_icon_size))
                        )
                    }
                }
            }
        },
        modifier = modifier
    )
}