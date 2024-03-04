package com.example.currencyconverterapp.converter.data.local

import com.example.currencyconverterapp.converter.data.model.ConverterCachedData
import com.example.currencyconverterapp.core.data.local.ProtoBufSerializer

object ConverterCachedDataSerializer
    : ProtoBufSerializer<ConverterCachedData>(ConverterCachedData::class)