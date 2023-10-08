package com.example.currencyconverterapp.model

import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRate(
    val code: String,
    val value: Double,
)
