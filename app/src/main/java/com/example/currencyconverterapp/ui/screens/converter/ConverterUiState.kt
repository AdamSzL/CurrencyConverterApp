package com.example.currencyconverterapp.ui.screens.converter

import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.ExchangeRate

data class ConverterUiState(
    val baseCurrency: Currency = defaultBaseCurrency,
    val baseCurrencyValue: Double = defaultBaseCurrencyValue,
    val targetCurrencies: List<Currency> = defaultTargetCurrencies,
    val selectedTargetCurrency: Currency = defaultBaseCurrency,
    val availableCurrencies: List<Currency> = defaultAvailableCurrencies,
    val exchangeRatesStatus: ExchangeRatesStatus = ExchangeRatesStatus.Success(defaultExchangeRates),
)

sealed interface ExchangeRatesStatus {
    data class Success(val exchangeRates: List<ExchangeRate>): ExchangeRatesStatus
    object Error: ExchangeRatesStatus
    object Loading: ExchangeRatesStatus
}