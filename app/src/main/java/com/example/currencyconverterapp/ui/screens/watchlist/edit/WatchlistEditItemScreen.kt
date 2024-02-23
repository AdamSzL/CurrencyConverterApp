package com.example.currencyconverterapp.ui.screens.watchlist.edit

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.data.defaultAvailableCurrencies
import com.example.currencyconverterapp.model.Currency

@Composable
fun WatchlistEditItemScreen(
    currencies: List<Currency>,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Edit watchlist item"
    )
}

@Preview
@Composable
fun WatchlistEditItemScreenPreview() {
    WatchlistEditItemScreen(
        currencies = defaultAvailableCurrencies,
    )
}