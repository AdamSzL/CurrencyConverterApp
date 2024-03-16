package com.example.currencyconverterapp.core.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun CurrencyConverterNavigationRail(
    currentCurrencyConverterScreen: CurrencyConverterScreen,
    navigateTo: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationRail(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.nav_top_margin)))
        screens.forEach { screen ->
            NavigationRailItem(
                selected = currentCurrencyConverterScreen.route == screen.route,
                onClick = {
                    navigateTo(screen.route)
                },
                icon = {
                    Image(
                        painter = painterResource(screen.icon),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                        contentDescription = screen.route
                    )
                },
                label = {
                    Text(
                        text = screen.route.lowercase().replaceFirstChar { it.uppercase() }
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun CurrencyConverterNavigationRailPreview() {
    CurrencyConverterAppTheme {
        CurrencyConverterNavigationRail(
            currentCurrencyConverterScreen = CurrencyConverterScreen.Converter,
            navigateTo = { },
        )
    }
}