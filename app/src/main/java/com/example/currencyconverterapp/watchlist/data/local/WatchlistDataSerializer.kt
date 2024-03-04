package com.example.currencyconverterapp.watchlist.data.local

import com.example.currencyconverterapp.core.data.local.ProtoBufSerializer
import com.example.currencyconverterapp.watchlist.data.model.WatchlistData

object WatchlistDataSerializer
    : ProtoBufSerializer<WatchlistData>(WatchlistData::class)
