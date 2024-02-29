package com.example.currencyconverterapp.ui.screens.converter

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.ui.screens.CurrencyConverterScreen
import com.example.currencyconverterapp.ui.screens.WatchlistSubScreen
import com.example.currencyconverterapp.ui.theme.Montserrat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterTopAppBar(
    currentCurrencyConverterScreen: CurrencyConverterScreen,
    currentWatchlistScreen: WatchlistSubScreen?,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(
                    (currentWatchlistScreen?.title ?: currentCurrencyConverterScreen.title)
                ),
                style = MaterialTheme.typography.titleLarge.copy(fontFamily = Montserrat),
            )
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        modifier = modifier
    )
}