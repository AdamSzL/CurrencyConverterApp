package com.example.currencyconverterapp.converter.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.core.presentation.components.AdaptiveFloatingActionButton
import com.example.currencyconverterapp.core.presentation.util.FabSize

@Composable
fun ConverterFloatingActionButton(
    size: FabSize,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AdaptiveFloatingActionButton(
        size = size,
        icon = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_target_currency)
            )
        },
        text = {
            Text(
                text = stringResource(R.string.add_target_currency)
            )
        },
        onClick = onClick,
        modifier = modifier
    )
}