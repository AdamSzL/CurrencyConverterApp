package com.example.currencyconverterapp.ui.screens.charts

import com.example.currencyconverterapp.data.model.Currency
import com.example.currencyconverterapp.data.model.TimePeriod
import com.example.currencyconverterapp.data.util.defaultBaseCurrency
import com.example.currencyconverterapp.data.util.defaultTargetCurrency

data class ChartsUiState(
    val selectedBaseCurrency: Currency = defaultBaseCurrency,
    val selectedTargetCurrency: Currency = defaultTargetCurrency,
    val isColumnChartEnabled: Boolean = false,
    val selectedTimePeriod: TimePeriod = TimePeriod.ONE_MONTH,
    val historicalExchangeRatesUiState: HistoricalExchangeRatesUiState = HistoricalExchangeRatesUiState.Loading,
    val shouldShowErrorMessage: Boolean = true,
)