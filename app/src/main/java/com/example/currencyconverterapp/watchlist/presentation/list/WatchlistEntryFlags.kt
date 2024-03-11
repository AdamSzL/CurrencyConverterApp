package com.example.currencyconverterapp.watchlist.presentation.list

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.converter.presentation.util.BaseControllerUtils
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.data.util.defaultBaseCurrency
import com.example.currencyconverterapp.core.data.util.defaultTargetCurrency
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme
import com.example.currencyconverterapp.watchlist.presentation.util.TriangleShape
import com.example.currencyconverterapp.watchlist.presentation.util.TriangleType

@Composable
fun WatchlistEntryFlags(
    context: Context,
    baseCurrency: Currency,
    targetCurrency: Currency,
    modifier: Modifier = Modifier
) {
    val baseCurrencyFlagPainter = painterResource(BaseControllerUtils.getFlagResourceByCurrencyCode(context, baseCurrency.code.lowercase()))
    val targetCurrencyFlagPainter = painterResource(BaseControllerUtils.getFlagResourceByCurrencyCode(context, targetCurrency.code.lowercase()))
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .drawBehind {
                drawLine(
                    start = Offset(
                        x = 0f,
                        y = size.height,
                    ),
                    end = Offset(
                        x = size.width,
                        y = 0f,
                    ),
                    color = Color.Black,
                )
            }
    ) {
        Image(
            painter = baseCurrencyFlagPainter,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = modifier
                .size(dimensionResource(R.dimen.flag_size))
                .clip(TriangleShape(TriangleType.TOP_LEFT))
        )
        Image(
            painter = targetCurrencyFlagPainter,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = modifier
                .size(dimensionResource(R.dimen.flag_size))
                .clip(TriangleShape(TriangleType.BOTTOM_RIGHT))
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WatchlistEntryFlagsPreview() {
    val context = LocalContext.current
    CurrencyConverterAppTheme {
        WatchlistEntryFlags(
            context = context,
            baseCurrency = defaultBaseCurrency,
            targetCurrency = defaultTargetCurrency,
        )
    }
}