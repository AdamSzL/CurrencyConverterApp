package com.example.currencyconverterapp.ui.screens.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.data.CurrencyConverterRepository
import com.example.currencyconverterapp.data.defaultBaseCurrency
import com.example.currencyconverterapp.data.defaultExchangeRates
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.ExchangeRate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val currencyConverterRepository: CurrencyConverterRepository
): ViewModel() {
    private val _converterUiState = MutableStateFlow(ConverterUiState())
    val converterUiState: StateFlow<ConverterUiState> = _converterUiState

    init {
        fetchExchangeRates()
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

    fun deleteConversionEntry(code: String) {
        _converterUiState.update {
            val deletedExchangeRate = it.exchangeRates.find { exchangeRate ->
                exchangeRate.code == code
            }
            it.copy(
                exchangeRates = it.exchangeRates
                    .filter { exchangeRate ->
                        exchangeRate.code != code
                    },
                deletedExchangeRate = deletedExchangeRate,
            )
        }
    }

    fun undoConversionEntryDeletion() {
        _converterUiState.update {
            it.copy(
                exchangeRates = if (it.deletedExchangeRate != null) {
                    (it.exchangeRates + ExchangeRate(code = it.deletedExchangeRate.code, value = it.deletedExchangeRate.value))
                        .sortedBy { exchangeRate ->
                            exchangeRate.code
                        }
                } else {
                    it.exchangeRates
                },
                deletedExchangeRate = null,
            )
        }
    }

}