package com.example.currencyconverterapp.ui.screens.converter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.data.model.Currency
import com.example.currencyconverterapp.data.model.ExchangeRate
import com.example.currencyconverterapp.data.repository.ConverterCachedDataRepository
import com.example.currencyconverterapp.data.repository.LatestExchangeRatesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val latestExchangeRatesRepository: LatestExchangeRatesRepository,
    private val converterCachedDataRepository: ConverterCachedDataRepository,
): ViewModel() {
    private val _converterUiState = MutableStateFlow(ConverterUiState())
    val converterUiState: StateFlow<ConverterUiState> = _converterUiState

    init {
        Log.d("XXX", "Converter ViewModel init")
        viewModelScope.launch {
            converterCachedDataRepository.savedConverterData.first { converterCachedData ->
                _converterUiState.update {
                    it.copy(
                        baseCurrency = converterCachedData.baseCurrency,
                        baseCurrencyValue = converterCachedData.baseCurrencyValue,
                        exchangeRates = converterCachedData.exchangeRates,
                        exchangeRatesUiState = ExchangeRatesUiState.Success,
                    )
                }
                fetchExchangeRates(
                    converterCachedData.baseCurrency,
                    converterCachedData.exchangeRates,
                )
                true
            }
        }
    }

    fun restoreToSuccessState() {
        _converterUiState.update {
            it.copy(
                exchangeRatesUiState = ExchangeRatesUiState.Success,
            )
        }
    }

    fun errorMessageDisplayed() {
        _converterUiState.update {
            it.copy(
                shouldShowErrorMessage = false
            )
        }
    }

    fun fetchExchangeRates(
        baseCurrency: Currency,
        exchangeRates: List<ExchangeRate>
    ) {
        viewModelScope.launch {
            var newExchangeRates = converterUiState.value.exchangeRates
            val exchangeRatesUiState = try {
                newExchangeRates = latestExchangeRatesRepository.getLatestExchangeRates(
                    baseCurrency = baseCurrency.code,
                    currencies = exchangeRates.joinToString(",") { it.code },
                ).data.values.toList()
                updateSavedExchangeRates(newExchangeRates)
                ExchangeRatesUiState.Success
            } catch (e: IOException) {
                delay(200)
                ExchangeRatesUiState.Error
            }
            _converterUiState.update {
                it.copy(
                    exchangeRates = newExchangeRates,
                    exchangeRatesUiState = exchangeRatesUiState,
                    shouldShowErrorMessage = true,
                )
            }
        }
    }


    fun selectBaseCurrency(currency: Currency) {
        var updatedExchangeRates: List<ExchangeRate> = listOf()
        _converterUiState.update {
            updatedExchangeRates = it.exchangeRates
                .filter { exchangeRate ->
                    exchangeRate.code != currency.code
                }
                .map { exchangeRate ->
                    exchangeRate.copy(value = null)
                }
            it.copy(
                baseCurrency = currency,
                exchangeRatesUiState = ExchangeRatesUiState.Success,
                exchangeRates = updatedExchangeRates,
            )
        }
        fetchExchangeRates(
            baseCurrency = currency,
            exchangeRates = updatedExchangeRates,
        )
        updateSavedExchangeRates(updatedExchangeRates)
        updateSavedBaseCurrency(currency)
    }

    fun setBaseCurrencyValue(value: Double) {
        _converterUiState.update {
            it.copy(baseCurrencyValue = value)
        }
        updateSavedCurrencyValue(value)
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
            updatedExchangeRates = (it.exchangeRates + ExchangeRate(
                currency.code,
                null
            )).sortedBy { exchangeRate ->
                exchangeRate.code
            }
            it.copy(
                exchangeRatesUiState = ExchangeRatesUiState.Success,
                exchangeRates = updatedExchangeRates,
                selectedTargetCurrency = null,
            )
        }
        fetchExchangeRates(
            baseCurrency = baseCurrency,
            exchangeRates = updatedExchangeRates
        )
        updateSavedExchangeRates(updatedExchangeRates)
        updateSavedBaseCurrency(baseCurrency)
    }

    fun deleteConversionEntry(code: String) {
        var updatedExchangeRates: List<ExchangeRate> = listOf()
        _converterUiState.update {
            val deletedExchangeRate = it.exchangeRates.find { exchangeRate ->
                exchangeRate.code == code
            }
            updatedExchangeRates = it.exchangeRates
                .filter { exchangeRate ->
                    exchangeRate.code != code
                }
            it.copy(
                exchangeRates = updatedExchangeRates,
                deletedExchangeRate = deletedExchangeRate,
            )
        }
        updateSavedExchangeRates(updatedExchangeRates)
    }

    fun undoConversionEntryDeletion() {
        var updatedExchangeRates: List<ExchangeRate> = emptyList()
        _converterUiState.update {
            updatedExchangeRates = if (it.deletedExchangeRate != null) {
                (it.exchangeRates + ExchangeRate(code = it.deletedExchangeRate.code, value = it.deletedExchangeRate.value))
                    .sortedBy { exchangeRate ->
                        exchangeRate.code
                    }
            } else {
                it.exchangeRates
            }
            it.copy(
                exchangeRates = updatedExchangeRates,
                deletedExchangeRate = null,
            )
        }
        updateSavedExchangeRates(updatedExchangeRates)
    }

    private fun updateSavedBaseCurrency(currency: Currency) {
        viewModelScope.launch {
            converterCachedDataRepository.updateSavedBaseCurrency(currency)
        }
    }

    private fun updateSavedCurrencyValue(value: Double) {
        viewModelScope.launch {
            converterCachedDataRepository.updateSavedCurrencyValue(value)
        }
    }

    private fun updateSavedExchangeRates(exchangeRates: List<ExchangeRate>) {
        viewModelScope.launch {
            converterCachedDataRepository.updateSavedExchangeRates(exchangeRates)
        }
    }
}