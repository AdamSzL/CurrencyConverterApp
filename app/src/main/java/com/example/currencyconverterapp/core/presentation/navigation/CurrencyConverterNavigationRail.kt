package com.example.currencyconverterapp.core.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.core.presentation.components.CurrencyConverterFabHandler
import com.example.currencyconverterapp.core.presentation.util.FabSize
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun CurrencyConverterNavigationRail(
    currentCurrencyConverterScreen: CurrencyConverterScreen,
    navigateTo: (String) -> Unit,
    modifier: Modifier = Modifier,
    fabAction: (() -> Unit)? = null,
) {
    NavigationRail(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxHeight()
        ) {
            CurrencyConverterFabHandler(
                currentCurrencyConverterScreen = currentCurrencyConverterScreen,
                size = FabSize.SMALL,
                fabAction = fabAction ?: { },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = dimensionResource(R.dimen.nav_margin))
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = dimensionResource(R.dimen.nav_margin))
            ) {
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
                                contentDescription = screen.route,
                                modifier = Modifier.size(dimensionResource(R.dimen.nav_rail_icon_size))
                            )
                        },
                        label = {
                            Text(
                                text = screen.route.lowercase().replaceFirstChar { it.uppercase() },
                            )
                        }
                    )
                }
            }
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
            fabAction = { }
        )
    }
}