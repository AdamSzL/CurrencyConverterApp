package com.example.currencyconverterapp.core.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun CurrencyConverterPermanentNavigationDrawer(
    currentCurrencyConverterScreen: CurrencyConverterScreen,
    navigateTo: (String) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    PermanentNavigationDrawer(
        drawerContent = {
            PermanentDrawerSheet(
                modifier = Modifier.width(dimensionResource(R.dimen.permanent_drawer_width))
            ) {
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.nav_top_margin)))
                screens.forEach { screen ->
                    NavigationDrawerItem(
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
                        selected = currentCurrencyConverterScreen.route == screen.route,
                        onClick = {
                            navigateTo(screen.route)
                        },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        },
        modifier = modifier
    ) {
        content()
    }
}

@Preview
@Composable
private fun CurrencyConverterPermanentNavigationDrawerPreview() {
    CurrencyConverterAppTheme {
        CurrencyConverterPermanentNavigationDrawer(
            currentCurrencyConverterScreen = CurrencyConverterScreen.Converter,
            navigateTo = { }
        ) {

        }
    }
}