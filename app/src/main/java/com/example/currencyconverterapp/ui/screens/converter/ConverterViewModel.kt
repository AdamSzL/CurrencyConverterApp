package com.example.currencyconverterapp.ui.screens.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.currencyconverterapp.CurrencyConverterApplication
import com.example.currencyconverterapp.data.CurrencyConverterRepository
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.ExchangeRate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.connection.Exchange
import java.io.IOException

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

    private fun fetchExchangeRates() {
        viewModelScope.launch {
            val latestExchangeRates = currencyConverterRepository.getLatestExchangeRates(
                baseCurrency = defaultBaseCurrency.code,
                currencies = defaultExchangeRates.joinToString(",") { it.code }
            ).data.values.toList()
            _converterUiState.update {
                it.copy(exchangeRates = latestExchangeRates)
            }
        }
    }

    fun selectBaseCurrency(currency: Currency) {
        _converterUiState.update {
            it.copy(
                baseCurrency = currency,
                exchangeRates = it.exchangeRates.filter { exchangeRate ->
                    exchangeRate.code != currency.code
                }
            )
        }
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

    fun addTargetCurrency(currency: Currency) {
        _converterUiState.update {
            it.copy(
                exchangeRates = it.exchangeRates + ExchangeRate(
                    currency.code,
                    null
                )
            )
        }
    }

    fun toggleSelectionMode() {
        _converterUiState.update {
            it.copy(isSelectionModeEnabled = !it.isSelectionModeEnabled)
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
            it.copy(exchangeRates = it.exchangeRates
                .filter { exchangeRate ->
                    exchangeRate.code !in it.selectedConversionEntryCodes
                }
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