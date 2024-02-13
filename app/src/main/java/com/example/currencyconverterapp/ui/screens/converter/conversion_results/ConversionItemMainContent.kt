package com.example.currencyconverterapp.ui.screens.converter.conversion_results

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.ExchangeRate
import java.text.NumberFormat

@Composable
fun RowScope.ConversionItemMainContent(
    baseCurrency: Currency,
    baseCurrencyValue: Double,
    targetCurrency: Currency,
    exchangeRate: ExchangeRate,
    modifier: Modifier = Modifier
) {
    val targetCurrencyFormat = getCurrencyFormat(targetCurrency)
    val baseCurrencyFormat = getCurrencyFormat(baseCurrency)

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.weight(1f)
    ) {
        Column {
            Text(
                text = targetCurrency.code,
                style = MaterialTheme.typography.displaySmall,
            )
            Text(
                text = targetCurrency.name,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center,
        ) {
            if (exchangeRate.value == null) {
                Box(
                    modifier = Modifier.size(dimensionResource(R.dimen.loading_icon_size))
                ) {
                    CircularProgressIndicator(
                        strokeWidth = dimensionResource(R.dimen.progress_indicator_stroke_width),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        trackColor = MaterialTheme.colorScheme.secondary,
                    )
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
    currencyFormat.maximumFractionDigits = 2
    val targetCurrencyInstance = java.util.Currency.getInstance(currency.code)
    currencyFormat.currency = targetCurrencyInstance
    return currencyFormat
}