package com.example.currencyconverterapp.watchlist.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class ExchangeRateRelation(val label: String) {
    LESS_THAN("Less than"),
    LESS_THAN_OR_EQUAL("Less than or equal to"),
    GREATER_THAN("Greater than"),
    GREATER_THAN_OR_EQUAL("Greater than or equal to");

    companion object {
        fun getByLabel(label: String): ExchangeRateRelation {
            return ExchangeRateRelation.entries.find { it.label == label } ?: LESS_THAN
        }
    }
}