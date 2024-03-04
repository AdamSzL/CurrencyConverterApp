package com.example.currencyconverterapp.watchlist.presentation.item.latest_exchange_rate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.data.util.defaultBaseCurrency
import com.example.currencyconverterapp.core.data.util.defaultTargetCurrency
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme
import com.example.currencyconverterapp.ui.theme.Montserrat

@Composable
fun LatestExchangeRatePanelStateHandler(
    baseCurrency: Currency,
    targetCurrency: Currency,
    latestExchangeRateUiState: LatestExchangeRateUiState,
    onLatestExchangeRateUpdate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        when (latestExchangeRateUiState) {
            is LatestExchangeRateUiState.Loading -> {
                CircularProgressIndicator(
                    strokeWidth = dimensionResource(R.dimen.progress_indicator_stroke_width),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    trackColor = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
            is LatestExchangeRateUiState.Error -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Text(
                        text = stringResource(R.string.failed_to_load_latest_exchange_rate),
                        style = MaterialTheme.typography.labelSmall.copy(fontFamily = Montserrat),
                    )
                    Button(
                        onClick = onLatestExchangeRateUpdate,
                    ) {
                        Text(
                            text = stringResource(R.string.retry)
                        )
                    }
                }
            }
            is LatestExchangeRateUiState.Success -> {
                LatestExchangeRatePanel(
                    baseCurrency = baseCurrency,
                    targetCurrency = targetCurrency,
                    latestExchangeRate = latestExchangeRateUiState.exchangeRate,
                    lastUpdatedAt = latestExchangeRateUiState.lastUpdatedAt,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LatestExchangeRatePanelSuccessStatePreview() {
    CurrencyConverterAppTheme {
        LatestExchangeRatePanelStateHandler(
            baseCurrency = defaultBaseCurrency,
            targetCurrency = defaultTargetCurrency,
            latestExchangeRateUiState = LatestExchangeRateUiState.Success(
                1.15,
                "2024-02-22T16:51:50Z"
            ),
            onLatestExchangeRateUpdate = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LatestExchangeRatePanelErrorStatePreview() {
    CurrencyConverterAppTheme {
        LatestExchangeRatePanelStateHandler(
            baseCurrency = defaultBaseCurrency,
            targetCurrency = defaultTargetCurrency,
            latestExchangeRateUiState = LatestExchangeRateUiState.Error,
            onLatestExchangeRateUpdate = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LatestExchangeRatePanelLoadingStatePreview() {
    CurrencyConverterAppTheme {
        LatestExchangeRatePanelStateHandler(
            baseCurrency = defaultBaseCurrency,
            targetCurrency = defaultTargetCurrency,
            latestExchangeRateUiState = LatestExchangeRateUiState.Loading,
            onLatestExchangeRateUpdate = { }
        )
    }
}
