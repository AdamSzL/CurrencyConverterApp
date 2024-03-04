package com.example.currencyconverterapp.charts.data.local

import com.example.currencyconverterapp.charts.data.model.ChartsCachedData
import com.example.currencyconverterapp.core.data.local.ProtoBufSerializer

object ChartsCachedDataSerializer
    : ProtoBufSerializer<ChartsCachedData>(ChartsCachedData::class)
