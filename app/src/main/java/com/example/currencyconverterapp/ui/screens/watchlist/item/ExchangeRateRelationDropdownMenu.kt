package com.example.currencyconverterapp.ui.screens.watchlist.item

import CustomDropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.currencyconverterapp.model.ExchangeRateRelation

@Composable
fun ExchangeRateRelationDropdownMenu(
    watchlistAddItemUiState: WatchlistItemUiState,
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