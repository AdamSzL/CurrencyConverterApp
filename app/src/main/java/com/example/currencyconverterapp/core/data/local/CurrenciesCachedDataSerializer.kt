package com.example.currencyconverterapp.core.data.local

import com.example.currencyconverterapp.core.data.model.CurrenciesCachedData

object CurrenciesCachedDataSerializer
    : ProtoBufSerializer<CurrenciesCachedData>(CurrenciesCachedData::class)
