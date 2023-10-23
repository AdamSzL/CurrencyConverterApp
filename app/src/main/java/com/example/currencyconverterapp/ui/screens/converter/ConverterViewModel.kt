package com.example.currencyconverterapp.ui.screens.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.currencyconverterapp.CurrencyConverterApplication
import com.example.currencyconverterapp.data.CurrencyConverterRepository
import com.example.currencyconverterapp.data.defaultBaseCurrency
import com.example.currencyconverterapp.data.defaultExchangeRates
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.ExchangeRate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConverterViewModel(
    private val currencyConverterRepository: CurrencyConverterRepository
): ViewModel() {
    private val _converterUiState = MutableStateFlow(ConverterUiState())
    val converterUiState: StateFlow<ConverterUiState> = _converterUiState

    init {
        fetchCurrencies()
        fetchExchangeRates()
    }

    private fun fetchCurrencies() {
        viewModelScope.launch {
            val currencies = currencyConverterRepository.getCurrencies().data.values.toList()
            _converterUiState.update {
                it.copy(availableCurrencies = currencies)
            }
        }
    }

    private fun fetchExchangeRates(
        baseCurrency: Currency = defaultBaseCurrency,
        previousExchangeRates: List<ExchangeRate> = defaultExchangeRates
    ) {
        viewModelScope.launch {
            val latestExchangeRates = currencyConverterRepository.getLatestExchangeRates(
                baseCurrency = baseCurrency.code,
                currencies = previousExchangeRates.joinToString(",") { it.code }
            ).data.values.toList()
            _converterUiState.update {
                it.copy(exchangeRates = latestExchangeRates)
            }
        }
    }

    fun selectBaseCurrency(currency: Currency) {
        var updatedExchangeRates: List<ExchangeRate> = listOf()
        _converterUiState.update {
            updatedExchangeRates = it.exchangeRates.filter { exchangeRate ->
                exchangeRate.code != currency.code
            }
            it.copy(
                baseCurrency = currency,
                exchangeRates = updatedExchangeRates,
            )
        }
        fetchExchangeRates(
            baseCurrency = currency,
            previousExchangeRates = updatedExchangeRates
        )
    }

    fun setBaseCurrencyValue(value: Double) {
        _converterUiState.update {
            it.copy(baseCurrencyValue = value)
        }
    }

    fun selectTargetCurrency(currency: Currency) {
        _converterUiState.update {
            it.copy(selectedTargetCurrency = currency)
        }
    }

    fun addTargetCurrency(
        baseCurrency: Currency,
        currency: Currency
    ) {
        var updatedExchangeRates: List<ExchangeRate> = listOf()
        _converterUiState.update {
            updatedExchangeRates = it.exchangeRates + ExchangeRate(
                currency.code,
                null
            )
            it.copy(
                exchangeRates = updatedExchangeRates,
                selectedTargetCurrency = null,
            )
        }
        fetchExchangeRates(
            baseCurrency = baseCurrency,
            previousExchangeRates = updatedExchangeRates
        )
    }

    fun toggleSelectionMode() {
        _converterUiState.update {
            val updatedIsSelectionModeEnabled = !it.isSelectionModeEnabled
            val selectedItems = if (updatedIsSelectionModeEnabled) it.selectedConversionEntryCodes else emptyList()
            it.copy(
                isSelectionModeEnabled = updatedIsSelectionModeEnabled,
                selectedConversionEntryCodes = selectedItems
            )
        }
    }

    fun toggleConversionEntrySelection(code: String, shouldAdd: Boolean) {
        _converterUiState.update {
            if (shouldAdd) {
                it.copy(selectedConversionEntryCodes = it.selectedConversionEntryCodes + code)
            } else {
                it.copy(selectedConversionEntryCodes = it.selectedConversionEntryCodes
                    .filter { targetCode ->
                        targetCode != code
                    }
                )
            }
        }
    }

    fun removeSelectedConversionEntries() {
        _converterUiState.update {
            it.copy(
                exchangeRates = it.exchangeRates
                    .filter { exchangeRate ->
                        exchangeRate.code !in it.selectedConversionEntryCodes
                    },
                selectedConversionEntryCodes = emptyList()
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CurrencyConverterApplication)
                val currencyConverterRepository = application.container.currencyConverterRepository
                ConverterViewModel(currencyConverterRepository = currencyConverterRepository)
            }
        }
    }
}