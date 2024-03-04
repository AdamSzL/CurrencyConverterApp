package com.example.currencyconverterapp.watchlist.presentation.item

import CustomDropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.currencyconverterapp.watchlist.data.model.ExchangeRateRelation

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