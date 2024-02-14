package com.example.currencyconverterapp.ui.screens.converter

import android.util.Log
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
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val currencyConverterRepository: CurrencyConverterRepository
): ViewModel() {
    private val _converterUiState = MutableStateFlow(ConverterUiState())
    val converterUiState: StateFlow<ConverterUiState> = _converterUiState

    init {
        fetchExchangeRates()
        Log.d("XXX", "Fetching exchange rates")
    }

    fun fetchExchangeRates(
        baseCurrency: Currency = defaultBaseCurrency,
        previousExchangeRates: List<ExchangeRate> = defaultExchangeRates
    ) {
        viewModelScope.launch {
            val exchangeRatesUiState = try {
                val latestExchangeRates = currencyConverterRepository.getLatestExchangeRates(
                    baseCurrency = baseCurrency.code,
                    currencies = previousExchangeRates.joinToString(",") { it.code }
                ).data.values.toList()
                ExchangeRatesUiState.Success(latestExchangeRates)
            } catch (e: IOException) {
                ExchangeRatesUiState.Error
            }
            _converterUiState.update {
                it.copy(exchangeRatesUiState = exchangeRatesUiState)
            }
        }
    }

    fun selectBaseCurrency(currency: Currency) {
        var updatedExchangeRates: List<ExchangeRate> = listOf()
        if (converterUiState.value.exchangeRatesUiState is ExchangeRatesUiState.Success) {
            _converterUiState.update {
                updatedExchangeRates = (it.exchangeRatesUiState as ExchangeRatesUiState.Success).exchangeRates.filter { exchangeRate ->
                    exchangeRate.code != currency.code
                }
                it.copy(
                    baseCurrency = currency,
                    exchangeRatesUiState = ExchangeRatesUiState.Success(updatedExchangeRates),
                )
            }
        } else {
            _converterUiState.update {
                it.copy(
                    baseCurrency = currency
                )
            }
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
        if (converterUiState.value.exchangeRatesUiState is ExchangeRatesUiState.Success) {
            _converterUiState.update {
                updatedExchangeRates = (it.exchangeRatesUiState as ExchangeRatesUiState.Success).exchangeRates + ExchangeRate(
                    currency.code,
                    null
                )
                it.copy(
                    exchangeRatesUiState = ExchangeRatesUiState.Success(updatedExchangeRates),
                    selectedTargetCurrency = null,
                )
            }
        }
        fetchExchangeRates(
            baseCurrency = baseCurrency,
            previousExchangeRates = updatedExchangeRates
        )
    }

    fun deleteConversionEntry(code: String) {
        if (converterUiState.value.exchangeRatesUiState is ExchangeRatesUiState.Success) {
            _converterUiState.update {
                val deletedExchangeRate = (it.exchangeRatesUiState as ExchangeRatesUiState.Success).exchangeRates.find { exchangeRate ->
                    exchangeRate.code == code
                }
                it.copy(
                    exchangeRatesUiState = ExchangeRatesUiState.Success((it.exchangeRatesUiState as ExchangeRatesUiState.Success).exchangeRates
                        .filter { exchangeRate ->
                            exchangeRate.code != code
                        }),
                    deletedExchangeRate = deletedExchangeRate,
                )
            }
        }
    }

    fun undoConversionEntryDeletion() {
        if (converterUiState.value.exchangeRatesUiState is ExchangeRatesUiState.Success) {
            _converterUiState.update {
                it.copy(
                    exchangeRatesUiState = ExchangeRatesUiState.Success(if (it.deletedExchangeRate != null) {
                        ((it.exchangeRatesUiState as ExchangeRatesUiState.Success).exchangeRates + ExchangeRate(code = it.deletedExchangeRate.code, value = it.deletedExchangeRate.value))
                            .sortedBy { exchangeRate ->
                                exchangeRate.code
                            }
                    } else {
                        (it.exchangeRatesUiState as ExchangeRatesUiState.Success).exchangeRates
                    }),
                    deletedExchangeRate = null,
                )
            }
        }
    }

}