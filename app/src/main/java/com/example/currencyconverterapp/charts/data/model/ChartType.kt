package com.example.currencyconverterapp.charts.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class ChartType(val label: String) {
    LINE("Line"),
    COLUMN("Column")
}