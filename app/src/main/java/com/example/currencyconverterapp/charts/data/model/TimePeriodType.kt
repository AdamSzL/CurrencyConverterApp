package com.example.currencyconverterapp.charts.data.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface TimePeriodType {
    @Serializable
    data class Recent(val recentTimePeriod: RecentTimePeriod) : TimePeriodType

    @Serializable
    data class Range(val start: String, val end: String): TimePeriodType
}