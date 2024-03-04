package com.example.currencyconverterapp.converter.presentation.base_controller

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.converter.presentation.util.BaseControllerUtils
import com.example.currencyconverterapp.core.data.model.Currency

@SuppressLint("DiscouragedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBoxScope.BaseCurrencyTextField(
    baseCurrency: Currency?,
    @StringRes label: Int?,
    expanded: Boolean,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(LocalTextInputService provides null) {
        OutlinedTextField(
            modifier = modifier.menuAnchor(),
            readOnly = true,
            singleLine = true,
            value = baseCurrency?.code ?: "",
            onValueChange = {},
            label = {
                if (label != null) {
                    Text(stringResource(label))
                }
            },
            textStyle = MaterialTheme.typography.displaySmall,
            leadingIcon = {
                val resource = BaseControllerUtils.getFlagResourceByCurrencyCode(LocalContext.current, baseCurrency?.code?.lowercase() ?: "FLAG")
                Image(
                    painter = painterResource(resource),
                    colorFilter = BaseControllerUtils.determineColorFilter(resource, MaterialTheme.colorScheme.onBackground),
                    contentDescription = baseCurrency?.name ?: "Empty flag",
                    modifier = Modifier.size(dimensionResource(R.dimen.small_flag_size))
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults
                    .TrailingIcon(expanded = expanded)
            },
        )
    }
}
