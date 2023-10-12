package com.example.currencyconverterapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.ui.screens.converter.ConverterScreen
import com.example.currencyconverterapp.ui.screens.converter.ConverterViewModel
import com.example.currencyconverterapp.ui.theme.Montserrat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterApp() {
    Scaffold(
        topBar = { CurrencyConverterTopAppBar() }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val converterViewModel: ConverterViewModel = viewModel(factory = ConverterViewModel.Factory)
            val converterUiState = converterViewModel.converterUiState.collectAsState().value
            ConverterScreen(
                converterUiState = converterUiState
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterTopAppBar(
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.currency_converter),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier
    )
}