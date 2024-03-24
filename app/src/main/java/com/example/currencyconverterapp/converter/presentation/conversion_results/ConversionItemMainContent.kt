package com.example.currencyconverterapp.converter.presentation.conversion_results

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
import com.example.currencyconverterapp.converter.presentation.ExchangeRatesUiState
import com.example.currencyconverterapp.converter.presentation.util.CurrencyUtils.getCurrencyFormat
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.data.model.ExchangeRate
import com.example.currencyconverterapp.core.presentation.util.ConversionResultsListItemSize

@Composable
fun RowScope.ConversionItemMainContent(
    conversionResultsListItemSize: ConversionResultsListItemSize,
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
        val (displayText, labelText) = if (conversionResultsListItemSize == ConversionResultsListItemSize.DEFAULT) {
            Pair(MaterialTheme.typography.displaySmall, MaterialTheme.typography.titleSmall)
        } else {
            Pair(MaterialTheme.typography.displayMedium, MaterialTheme.typography.titleMedium)
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = targetCurrency.code,
                style = displayText,
                maxLines = 1,
            )
            Text(
                text = targetCurrency.name,
                style = labelText,
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
                    style = displayText,
                )
                Text(
                    text = "${targetCurrencyFormat.format(1)} " +
                            "= ${baseCurrencyFormat.format(1 / exchangeRate.value)}",
                    style = labelText,
                )
            }
        }
    }
}
