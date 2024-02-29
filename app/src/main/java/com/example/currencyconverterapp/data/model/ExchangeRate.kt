package com.example.currencyconverterapp.data.model

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ExchangeRate(
    val code: String,
    val value: Double?,
    val id: String = UUID.randomUUID().toString()
)
