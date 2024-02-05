package com.example.currencyconverterapp.ui.screens.converter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.currencyconverterapp.ui.CurrencyConverterScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterTopAppBar(
    currentScreen: CurrencyConverterScreen,
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
            }
        },
        modifier = modifier
    )
}