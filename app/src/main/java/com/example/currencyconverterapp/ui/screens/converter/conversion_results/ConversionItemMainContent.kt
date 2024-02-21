package com.example.currencyconverterapp.ui.screens.converter.conversion_results

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.ExchangeRate
import com.example.currencyconverterapp.ui.screens.converter.ExchangeRatesUiState
import java.text.NumberFormat

@Composable
fun RowScope.ConversionItemMainContent(
    exchangeRatesUiState: ExchangeRatesUiState,
    baseCurrency: Currency,
    baseCurrencyValue: Double,
    targetCurrency: Currency,
    exchangeRate: ExchangeRate,
    onExchangeRatesRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    val targetCurrencyFormat = getCurrencyFormat(targetCurrency)
    val baseCurrencyFormat = getCurrencyFormat(baseCurrency)

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.weight(1f)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = targetCurrency.code,
                style = MaterialTheme.typography.displaySmall,
                maxLines = 1,
            )
            Text(
                text = targetCurrency.name,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.currency_code_value_gap)))
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center,
        ) {
            if (exchangeRate.value == null) {
                Box(
                    modifier = Modifier.size(dimensionResource(R.dimen.loading_icon_size))
                ) {
                    if (exchangeRatesUiState == ExchangeRatesUiState.Success) {
                        CircularProgressIndicator(
                            strokeWidth = dimensionResource(R.dimen.progress_indicator_stroke_width),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            trackColor = MaterialTheme.colorScheme.secondary,
                        )
                    } else {
                        IconButton(
                            onClick = onExchangeRatesRefresh
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Refresh,
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = stringResource(R.string.refresh),
                            )
                        }
                    }
                }
            } else {
                Text(
                    text = targetCurrencyFormat.format(baseCurrencyValue * exchangeRate.value),
                    style = MaterialTheme.typography.displaySmall,
                )
                Text(
                    text = "${targetCurrencyFormat.format(1)} " +
                            "= ${baseCurrencyFormat.format(1 / exchangeRate.value)}",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

fun getCurrencyFormat(currency: Currency): NumberFormat {
    val currencyFormat = NumberFormat.getCurrencyInstance()
    currencyFormat.maximumFractionDigits = 4
    val targetCurrencyInstance = java.util.Currency.getInstance(currency.code)
    currencyFormat.currency = targetCurrencyInstance
    return currencyFormat
}