package com.example.currencyconverterapp.watchlist.presentation.item

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.converter.presentation.util.BaseControllerUtils
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.data.util.defaultBaseCurrency
import com.example.currencyconverterapp.core.data.util.defaultTargetCurrency
import com.example.currencyconverterapp.core.presentation.util.WatchlistItemScreenContentType
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun WatchlistItemCurrencyFlags(
    context: Context,
    baseCurrency: Currency,
    targetCurrency: Currency,
    watchlistItemScreenContentType: WatchlistItemScreenContentType,
    modifier: Modifier = Modifier
) {
    val flagSize = if (watchlistItemScreenContentType == WatchlistItemScreenContentType.SMALL_FONT) {
        R.dimen.flag_size
    } else {
        R.dimen.big_flag_size
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(BaseControllerUtils.getFlagResourceByCurrencyCode(context, baseCurrency.code.lowercase())),
            contentDescription = stringResource(R.string.base_currency),
            modifier = Modifier
                .size(dimensionResource(flagSize))
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.exchange_rate_panel_small_gap)))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.exchange_rate_panel_small_gap)))
        Image(
            painter = painterResource(BaseControllerUtils.getFlagResourceByCurrencyCode(context, targetCurrency.code.lowercase())),
            contentDescription = stringResource(R.string.target_currency),
            modifier = Modifier
                .size(dimensionResource(flagSize))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WatchlistItemCurrencyFlagsPreview(

) {
    CurrencyConverterAppTheme {
        WatchlistItemCurrencyFlags(
            context = LocalContext.current,
            baseCurrency = defaultBaseCurrency,
            targetCurrency = defaultTargetCurrency,
            watchlistItemScreenContentType = WatchlistItemScreenContentType.SMALL_FONT
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WatchlistItemCurrencyFlagsBigFontPreview(
) {
    CurrencyConverterAppTheme {
        WatchlistItemCurrencyFlags(
            context = LocalContext.current,
            baseCurrency = defaultBaseCurrency,
            targetCurrency = defaultTargetCurrency,
            watchlistItemScreenContentType = WatchlistItemScreenContentType.BIG_FONT
        )
    }
}
