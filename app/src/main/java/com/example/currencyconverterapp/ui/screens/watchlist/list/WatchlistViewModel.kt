package com.example.currencyconverterapp.ui.screens.watchlist.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.data.WatchlistDataRepository
import com.example.currencyconverterapp.model.WatchlistItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val watchlistDataRepository: WatchlistDataRepository
): ViewModel() {

    val watchlistItems: StateFlow<List<WatchlistItem>> = watchlistDataRepository.watchlistData
        .map { watchlistData -> watchlistData.watchlistItems }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun removeWatchlistItem(itemId: String) {
        viewModelScope.launch {
            watchlistDataRepository.removeWatchlistItem(itemId)
        }
    }
}