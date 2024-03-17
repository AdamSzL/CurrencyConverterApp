package com.example.currencyconverterapp.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.core.presentation.util.FabSize
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun AdaptiveFloatingActionButton(
    size: FabSize,
    text: @Composable () -> Unit,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (size) {
        FabSize.EXTENDED -> {
            ExtendedFloatingActionButton(
                onClick = onClick,
                icon = {
                    icon()
                },
                text = {
                    text()
                },
                modifier = modifier
            )
        }
        FabSize.SMALL -> {
            FloatingActionButton(
                onClick = onClick,
                modifier = modifier
            ) {
                icon()
            }
        }
    }
}

@Preview
@Composable
private fun AdaptiveExtendedFloatingActionButtonPreview() {
    CurrencyConverterAppTheme {
        AdaptiveFloatingActionButton(
            size = FabSize.EXTENDED,
            text = {
                Text(
                    text = "Some Action"
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            },
            onClick = { }
        )
    }
}

@Preview
@Composable
private fun AdaptiveSmallFloatingActionButtonPreview() {
    CurrencyConverterAppTheme {
        AdaptiveFloatingActionButton(
            size = FabSize.SMALL,
            text = {
                Text(
                    text = "Some Action"
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            },
            onClick = { }
        )
    }
}
