package com.example.currencyconverterapp.charts.presentation

import com.example.currencyconverterapp.charts.data.model.RecentTimePeriod
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.data.util.defaultBaseCurrency
import com.example.currencyconverterapp.core.data.util.defaultTargetCurrency

data class ChartsUiState(
    val selectedBaseCurrency: Currency = defaultBaseCurrency,
    val selectedTargetCurrency: Currency = defaultTargetCurrency,
    val isColumnChartEnabled: Boolean = false,
    val selectedRecentTimePeriod: RecentTimePeriod = RecentTimePeriod.ONE_MONTH,
    val historicalExchangeRatesUiState: HistoricalExchangeRatesUiState = HistoricalExchangeRatesUiState.Loading,
    val shouldShowErrorMessage: Boolean = true,
)