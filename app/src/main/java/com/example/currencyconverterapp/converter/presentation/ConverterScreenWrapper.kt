package com.example.currencyconverterapp.converter.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.currencyconverterapp.converter.presentation.util.ConverterScreenActions
import com.example.currencyconverterapp.core.presentation.CurrenciesUiState
import com.example.currencyconverterapp.core.presentation.components.ScreenAdaptiveNavigationWrapper
import com.example.currencyconverterapp.core.presentation.navigation.ScreenAdaptiveNavigationWrapperProps
import com.example.currencyconverterapp.core.presentation.util.ConversionResultsListItemSize
import com.example.currencyconverterapp.core.presentation.util.ConverterAddCurrencyContainerType
import com.example.currencyconverterapp.core.presentation.util.FloatingActionButtonType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterScreenWrapper(
    converterUiState: ConverterUiState,
    converterBaseCurrencyValue: String,
    currenciesUiState: CurrenciesUiState,
    fabType: FloatingActionButtonType,
    screenAdaptiveNavigationWrapperProps: ScreenAdaptiveNavigationWrapperProps,
    conversionResultsListItemSize: ConversionResultsListItemSize,
    converterAddCurrencyContainerType: ConverterAddCurrencyContainerType,
    converterScreenActions: ConverterScreenActions,
    modifier: Modifier = Modifier,
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        )
    )
    var isAddCurrencyPanelVisible by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    ScreenAdaptiveNavigationWrapper(
        screenAdaptiveNavigationWrapperProps = screenAdaptiveNavigationWrapperProps,
        fabAction = {
            if (converterAddCurrencyContainerType == ConverterAddCurrencyContainerType.BOTTOM_SHEET) {
                scope.launch {
                    bottomSheetScaffoldState.bottomSheetState.expand()
                }
            } else if (converterAddCurrencyContainerType == ConverterAddCurrencyContainerType.SIDE_PANEL) {
                isAddCurrencyPanelVisible = !isAddCurrencyPanelVisible
            }
        },
        modifier = modifier
    ) {
        ConverterScreen(
            converterUiState = converterUiState,
            converterBaseCurrencyValue = converterBaseCurrencyValue,
            bottomSheetScaffoldState = bottomSheetScaffoldState,
            isAddCurrencyPanelVisible = isAddCurrencyPanelVisible,
            fabType = fabType,
            conversionResultsListItemSize = conversionResultsListItemSize,
            converterAddCurrencyContainerType = converterAddCurrencyContainerType,
            availableCurrencies = (currenciesUiState as CurrenciesUiState.Success).currencies,
            converterScreenActions = converterScreenActions,
            onAddCurrencySidePanelToggle = {
                isAddCurrencyPanelVisible = !isAddCurrencyPanelVisible
            }
        )
    }
}