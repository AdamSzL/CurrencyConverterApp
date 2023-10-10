package com.example.currencyconverterapp.ui.screens.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.currencyconverterapp.CurrencyConverterApplication
import com.example.currencyconverterapp.data.CurrencyConverterRepository
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

    private fun fetchExchangeRates() {
        viewModelScope.launch {
            val latestExchangeRates = currencyConverterRepository.getLatestExchangeRates(
                baseCurrency = defaultBaseCurrency.code,
                currencies = defaultTargetCurrencies.joinToString(",")
            ).data.values.toList()
            _converterUiState.update {
                it.copy(exchangeRates = latestExchangeRates)
            }
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