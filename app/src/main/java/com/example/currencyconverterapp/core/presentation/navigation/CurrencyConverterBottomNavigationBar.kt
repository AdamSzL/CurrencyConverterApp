package com.example.currencyconverterapp.core.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

val screens = listOf(
    CurrencyConverterScreen.Converter,
    CurrencyConverterScreen.Charts,
    CurrencyConverterScreen.Watchlist
)

@Composable
fun CurrencyConverterBottomNavigationBar(
    currentScreen: CurrencyConverterScreen,
    navigateTo: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        screens.forEach { screen ->
            NavigationBarItem(
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
                },
                selected = currentScreen.route == screen.route,
                onClick = { navigateTo(screen.route) }
            )
        }
    }
}

@Preview
@Composable
private fun CurrencyConverterBottomNavigationBarPreview() {
    CurrencyConverterAppTheme {
        CurrencyConverterBottomNavigationBar(
            currentScreen = CurrencyConverterScreen.Converter,
            navigateTo = { }
        )
    }
}