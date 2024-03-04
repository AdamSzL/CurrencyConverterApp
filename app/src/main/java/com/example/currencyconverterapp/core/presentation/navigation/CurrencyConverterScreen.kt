package com.example.currencyconverterapp.core.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.currencyconverterapp.R

enum class CurrencyConverterScreen(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    Converter(
        route = "converter",
        title = R.string.converter,
        icon = R.drawable.ic_money,
    ),
    Charts(
        route = "charts",
        title = R.string.charts,
        icon = R.drawable.ic_chart,
    ),
    Watchlist(
        route = "watchlist",
        title = R.string.watchlist,
        icon = R.drawable.ic_watchlist,
    )
}
