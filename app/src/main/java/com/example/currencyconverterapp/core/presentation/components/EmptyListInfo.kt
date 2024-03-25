package com.example.currencyconverterapp.core.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun EmptyListInfo(
    @DrawableRes icon: Int,
    @StringRes iconDescription: Int?,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.watchlist_add_main_margin))
    ) {

        Image(
            painter = painterResource(icon),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
            contentDescription = if (iconDescription != null) stringResource(iconDescription) else null,
            modifier = Modifier.size(dimensionResource(R.dimen.watchlist_icon_big))
        )

        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.displayMedium,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyListInfoPreview() {
    CurrencyConverterAppTheme {
        EmptyListInfo(
            icon = R.drawable.ic_watchlist,
            iconDescription = null,
            text = R.string.watchlist_empty,
        )
    }
}