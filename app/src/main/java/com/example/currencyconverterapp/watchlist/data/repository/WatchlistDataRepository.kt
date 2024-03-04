package com.example.currencyconverterapp.watchlist.data.repository

import androidx.datastore.core.DataStore
import com.example.currencyconverterapp.watchlist.data.model.WatchlistData
import com.example.currencyconverterapp.watchlist.data.model.WatchlistItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface WatchlistDataRepository {

    val watchlistData: Flow<WatchlistData>

    suspend fun addWatchlistItem(watchlistItem: WatchlistItem)

    suspend fun removeWatchlistItem(itemId: String)

    suspend fun updateWatchlistItem(watchlistItem: WatchlistItem)
}

class WatchlistDataRepositoryImpl @Inject constructor(
    private val watchlistDataStore: DataStore<WatchlistData>
): WatchlistDataRepository {

    override val watchlistData: Flow<WatchlistData>
        get() = watchlistDataStore.data

    override suspend fun addWatchlistItem(watchlistItem: WatchlistItem) {
        watchlistDataStore.updateData { watchlistData ->
            watchlistData.copy(watchlistItems = watchlistData.watchlistItems + watchlistItem)
        }
    }

    override suspend fun removeWatchlistItem(itemId: String) {
        watchlistDataStore.updateData { watchlistData ->
            watchlistData.copy(
                watchlistItems = watchlistData.watchlistItems.filter { it.id != itemId }
            )
        }
    }

    override suspend fun updateWatchlistItem(watchlistItem: WatchlistItem) {
        watchlistDataStore.updateData { watchlistData ->
            val mutableItems = watchlistData.watchlistItems.toMutableList()
            val itemIndex = mutableItems.indexOfFirst { it.id == watchlistItem.id }
            mutableItems[itemIndex] = watchlistItem
            watchlistData.copy(
                watchlistItems = mutableItems
            )
        }
    }
}