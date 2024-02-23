package com.example.currencyconverterapp.ui.screens.watchlist

import CustomDropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.currencyconverterapp.model.ExchangeRateRelation
import com.example.currencyconverterapp.ui.screens.watchlist.add.WatchlistAddItemUiState

@Composable
fun ExchangeRateRelationDropdownMenu(
    watchlistAddItemUiState: WatchlistAddItemUiState,
    onExchangeRateRelationSelection: (ExchangeRateRelation) -> Unit,
    modifier: Modifier = Modifier
) {
    CustomDropdownMenu(
        items = ExchangeRateRelation.entries.map { it.label },
        label = null,
        selectedItemLabel = watchlistAddItemUiState.exchangeRateRelation.label,
        getItemByString = { label -> ExchangeRateRelation.getByLabel(label) },
        onItemSelection = onExchangeRateRelationSelection,
        modifier = modifier
    )
}