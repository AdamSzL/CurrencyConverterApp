package com.example.currencyconverterapp.ui.screens.charts

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.currencyconverterapp.model.Currency

@Composable
fun ChartsScreen(
    currencies: List<Currency>,
    modifier: Modifier = Modifier
) {
    Text(
        text = currencies.size.toString()
    )
}