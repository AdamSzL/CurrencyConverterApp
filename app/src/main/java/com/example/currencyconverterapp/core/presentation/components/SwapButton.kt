package com.example.currencyconverterapp.core.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun SwapButton(
    @StringRes contentDescription: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    rotation: Float = 0.0f,
) {
    FilledIconButton(
        onClick = onClick,
        modifier = modifier
            .rotate(rotation)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_swap),
            contentDescription = stringResource(contentDescription),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
            modifier = Modifier
                .padding(dimensionResource(R.dimen.swap_icon_margin))
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SwapCurrenciesButtonPreview() {
    CurrencyConverterAppTheme {
        SwapButton(
            contentDescription = R.string.swap_currencies,
            rotation = 0.0f,
            onClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SwapCurrenciesButtonRotation90DegPreview() {
    CurrencyConverterAppTheme {
        SwapButton(
            contentDescription = R.string.swap_currencies,
            rotation = 90.0f,
            onClick = { }
        )
    }
}
