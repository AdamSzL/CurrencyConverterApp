package com.example.currencyconverterapp.ui.screens.converter.base_controller

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverterapp.R

@Composable
fun CurrencyValueTextField(
    currencyValue: Double,
    onValueChange: (Double) -> Unit,
    modifier: Modifier = Modifier,
) {
    var text by remember { mutableStateOf(currencyValue.toString()) }
    var isError by remember { mutableStateOf(false) }

    val alpha: Float by animateFloatAsState(
        targetValue = if (isError) {
            1f
        } else {
            0f
        },
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing,
        ), label = "input error"
    )

    OutlinedTextField(
        value = text,
        singleLine = true,
        onValueChange = { input ->
            text = input
            isError = try {
                onValueChange(text.toDouble())
                false
            } catch (e: NumberFormatException) {
                true
            }
        },
        isError = isError,
        label = { Text(stringResource(R.string.value)) },
        textStyle = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Normal),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        supportingText = {
            Text(
                text = stringResource(R.string.enter_decimal_number),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(alpha)
            )
        },
        trailingIcon = {
            if (isError) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = stringResource(R.string.error),
                )
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun CurrencyValueTextFieldPreview() {
    CurrencyValueTextField(
        currencyValue = 0.2,
        onValueChange = { },
    )
}