package com.example.currencyconverterapp.ui

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
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.ui.screens.converter.ConverterScreen
import com.example.currencyconverterapp.ui.screens.converter.ConverterViewModel

@Composable
fun CurrencyConverterApp(
    converterViewModel: ConverterViewModel = viewModel(factory = ConverterViewModel.Factory)
) {
    val converterUiState = converterViewModel.converterUiState.collectAsState().value
    Scaffold(
        topBar = {
            CurrencyConverterTopAppBar(
                isSelectionModeEnabled = converterUiState.isSelectionModeEnabled,
                onSelectionModeToggle = converterViewModel::toggleSelectionMode,
                onSelectedTargetCurrenciesDeletion = converterViewModel::removeSelectedConversionEntries,
            )
        },
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            ConverterScreen(
                converterUiState = converterUiState,
                onBaseCurrencySelection = converterViewModel::selectBaseCurrency,
                onBaseCurrencyValueChange = converterViewModel::setBaseCurrencyValue,
                onTargetCurrencySelection = converterViewModel::selectTargetCurrency,
                onTargetCurrencyAddition = converterViewModel::addTargetCurrency,
                onSelectionModeToggle = converterViewModel::toggleSelectionMode,
                onConversionEntryToggle = converterViewModel::toggleConversionEntrySelection,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterTopAppBar(
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
                    text = stringResource(R.string.currency_converter),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.align(Alignment.CenterStart),
                )
                if (isSelectionModeEnabled) {
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