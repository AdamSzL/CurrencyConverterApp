package com.example.currencyconverterapp.converter.presentation.bottom_sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.currencyconverterapp.core.data.model.Currency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCurrencyBottomSheet(
    currencies: List<Currency>,
    selectedTargetCurrency: Currency?,
    sheetScaffoldState: BottomSheetScaffoldState,
    onTargetCurrencySelection: (Currency) -> Unit,
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = sheetScaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            Column {
                AddCurrencyBottomSheetHeader()
                AddCurrencyBottomSheetForm(
                    currencies = currencies,
                    selectedTargetCurrency = selectedTargetCurrency,
                    onTargetCurrencySelection = onTargetCurrencySelection,
                    onCancel = onCancel,
                    onSubmit = onSubmit,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    ) {
        content()
    }
}