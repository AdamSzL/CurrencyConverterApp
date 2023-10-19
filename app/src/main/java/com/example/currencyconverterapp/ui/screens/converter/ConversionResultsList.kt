package com.example.currencyconverterapp.ui.screens.converter

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.ExchangeRate
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme
import java.text.NumberFormat

@Composable
fun ConversionResultsList(
    currencies: List<Currency>,
    baseCurrency: Currency,
    baseCurrencyValue: Double,
    exchangeRates: List<ExchangeRate>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.converter_margin))
    ) {
        itemsIndexed(exchangeRates) { index, exchangeRate ->
            Divider()
            ConversionResultsListItem(
                baseCurrency = baseCurrency,
                targetCurrency = currencies.find { it.code == exchangeRate.code }!!,
                baseCurrencyValue = baseCurrencyValue,
                exchangeRate = exchangeRate,
                modifier = Modifier
                    .fillMaxWidth()
            )
            if (index == exchangeRates.size - 1) {
                Divider()
            }
        }
    }
}

@Composable
fun ConversionResultsListItem(
    baseCurrency: Currency,
    targetCurrency: Currency,
    baseCurrencyValue: Double,
    exchangeRate: ExchangeRate,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(dimensionResource(R.dimen.conversion_result_item_margin))
    ) {
        val targetCurrencyFormat = getCurrencyFormat(targetCurrency)
        val baseCurrencyFormat = getCurrencyFormat(baseCurrency)

        val context = LocalContext.current
        Image(
            painter = painterResource(getFlagResourceByCurrencyCode(context, exchangeRate.code.lowercase())),
            contentDescription = null,
            modifier = Modifier.size(dimensionResource(R.dimen.flag_size)),
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.conversion_result_item_flag_gap)))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
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
                horizontalAlignment = Alignment.End
            ) {
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

@Preview(showBackground = true)
@Composable
fun ConversionResultsListPreview() {
    CurrencyConverterAppTheme {
        ConversionResultsList(
            currencies = defaultAvailableCurrencies,
            baseCurrency = defaultBaseCurrency,
            baseCurrencyValue = defaultBaseCurrencyValue,
            exchangeRates = defaultExchangeRates,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConversionResultsListItemPreview() {
    CurrencyConverterAppTheme {
        ConversionResultsListItem(
            baseCurrency = defaultBaseCurrency,
            targetCurrency = defaultAvailableCurrencies.find { it.code == "GBP" }!!,
            baseCurrencyValue = defaultBaseCurrencyValue,
            exchangeRate = defaultExchangeRates.first(),
        )
    }
}