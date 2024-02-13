package com.example.currencyconverterapp.ui.screens.charts

import com.example.currencyconverterapp.data.defaultBaseCurrency
import com.example.currencyconverterapp.data.defaultTargetCurrency
import com.example.currencyconverterapp.model.Currency

data class ChartsUiState(
    val selectedBaseCurrency: Currency = defaultBaseCurrency,
    val selectedTargetCurrency: Currency = defaultTargetCurrency,
    val isColumnChartEnabled: Boolean = false,
    val selectedTimePeriod: TimePeriod = TimePeriod.ONE_MONTH,
    val historicalExchangeRatesUiState: HistoricalExchangeRatesUiState = HistoricalExchangeRatesUiState.Loading
)