package com.example.currencyconverterapp.ui.screens.converter.currencies_dropdown

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.data.model.Currency
import com.example.currencyconverterapp.ui.screens.converter.base_controller.BaseControllerUtils.determineColorFilter
import com.example.currencyconverterapp.ui.screens.converter.base_controller.BaseControllerUtils.getFlagResourceByCurrencyCode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyDropdownMenuItem(
    onItemClicked: () -> Unit,
    currency: Currency,
    modifier: Modifier = Modifier
) {
    DropdownMenuItem(
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val resource = getFlagResourceByCurrencyCode(LocalContext.current, currency.code.lowercase())
                    Image(
                        painter = painterResource(resource),
                        colorFilter = determineColorFilter(resource, MaterialTheme.colorScheme.onBackground),
                        contentDescription = currency.name,
                        modifier = Modifier.size(dimensionResource(R.dimen.small_flag_size))
                    )
                    Spacer(
                        modifier = Modifier
                            .width(dimensionResource(R.dimen.dropdown_menu_item_flag_code_gap))
                    )
                    Text(
                        text = currency.code,
                        style = MaterialTheme.typography.displaySmall,
                    )
                }
                Spacer(
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.dropdown_menu_item_code_name_gap))
                )
                Text(
                    text = currency.name,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        },
        onClick = onItemClicked,
        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
        modifier = modifier
    )
}