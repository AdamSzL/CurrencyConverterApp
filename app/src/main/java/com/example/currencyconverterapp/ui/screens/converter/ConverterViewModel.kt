package com.example.currencyconverterapp.ui.screens.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.data.ConverterUserPreferencesRepository
import com.example.currencyconverterapp.data.CurrencyConverterRepository
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.ExchangeRate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val currencyConverterRepository: CurrencyConverterRepository,
    private val converterUserPreferencesRepository: ConverterUserPreferencesRepository
): ViewModel() {
    private val _converterUiState = MutableStateFlow(ConverterUiState())
    val converterUiState: StateFlow<ConverterUiState> = _converterUiState

    init {
        fetchExchangeRatesBySavedData()
    }

    fun fetchExchangeRatesBySavedData(
        baseCurrency: Currency? = null,
    ) {
        viewModelScope.launch {
            converterUserPreferencesRepository.savedBaseCurrency.zip(
                converterUserPreferencesRepository.savedCurrencyValue
            ) { savedBaseCurrency, savedCurrencyValue ->
                CombinedBaseCurrencyData(
                    Json.decodeFromString(savedBaseCurrency),
                    savedCurrencyValue
                )
            }.zip(converterUserPreferencesRepository.savedCurrencies) { combinedBaseCurrencyData, savedCurrencies ->
                CombinedPrefsData(combinedBaseCurrencyData, savedCurrencies)
            }.first { savedData ->
                _converterUiState.update {
                    it.copy(
                        baseCurrency = savedData.combinedBaseCurrencyData.currency,
                        baseCurrencyValue = savedData.combinedBaseCurrencyData.currencyValue
                    )
                }
                fetchExchangeRates(
                    baseCurrency ?: savedData.combinedBaseCurrencyData.currency,
                    savedData.savedCurrencies
                )
                true
            }
        }
    }

    private fun fetchExchangeRates(
        baseCurrency: Currency,
        currencyCodes: String,
    ) {
        viewModelScope.launch {
            val exchangeRatesUiState = try {
                val latestExchangeRates = currencyConverterRepository.getLatestExchangeRates(
                    baseCurrency = baseCurrency.code,
                    currencies = currencyCodes,
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

    private fun updateSavedCurrencies(currencies: String) {
        viewModelScope.launch {
            converterUserPreferencesRepository.updateSavedCurrencies(currencies)
        }
    }

    private fun updateSavedBaseCurrency(currency: Currency) {
        viewModelScope.launch {
            converterUserPreferencesRepository.updateSavedBaseCurrency(currency)
        }
    }

    private fun updateSavedCurrencyValue(value: Double) {
        viewModelScope.launch {
            converterUserPreferencesRepository.updateSavedCurrencyValue(value)
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
            currencyCodes = updatedExchangeRates.joinToString(",") { it.code }
        )
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
        val newCurrencyCodes = updatedExchangeRates.joinToString(",") { it.code }
        fetchExchangeRates(
            baseCurrency = baseCurrency,
            currencyCodes = newCurrencyCodes,
        )
        updateSavedCurrencies(newCurrencyCodes)
        updateSavedBaseCurrency(baseCurrency)
    }

    fun deleteConversionEntry(code: String) {
        var updatedExchangeRates: List<ExchangeRate> = listOf()
        if (converterUiState.value.exchangeRatesUiState is ExchangeRatesUiState.Success) {
            _converterUiState.update {
                val deletedExchangeRate = (it.exchangeRatesUiState as ExchangeRatesUiState.Success).exchangeRates.find { exchangeRate ->
                    exchangeRate.code == code
                }
                updatedExchangeRates = (it.exchangeRatesUiState as ExchangeRatesUiState.Success).exchangeRates
                    .filter { exchangeRate ->
                        exchangeRate.code != code
                    }
                it.copy(
                    exchangeRatesUiState = ExchangeRatesUiState.Success(updatedExchangeRates),
                    deletedExchangeRate = deletedExchangeRate,
                )
            }
        }
        updateSavedCurrencies(updatedExchangeRates.joinToString(",") { it.code })
    }

    fun undoConversionEntryDeletion() {
        var updatedExchangeRates: List<ExchangeRate> = emptyList()
        if (converterUiState.value.exchangeRatesUiState is ExchangeRatesUiState.Success) {
            _converterUiState.update {
                updatedExchangeRates = if (it.deletedExchangeRate != null) {
                    ((it.exchangeRatesUiState as ExchangeRatesUiState.Success).exchangeRates + ExchangeRate(code = it.deletedExchangeRate.code, value = it.deletedExchangeRate.value))
                        .sortedBy { exchangeRate ->
                            exchangeRate.code
                        }
                } else {
                    (it.exchangeRatesUiState as ExchangeRatesUiState.Success).exchangeRates
                }
                it.copy(
                    exchangeRatesUiState = ExchangeRatesUiState.Success(updatedExchangeRates),
                    deletedExchangeRate = null,
                )
            }
        }
        updateSavedCurrencies(updatedExchangeRates.joinToString(",") { it.code })
    }

}

data class CombinedPrefsData(
    val combinedBaseCurrencyData: CombinedBaseCurrencyData,
    val savedCurrencies: String,
)

data class CombinedBaseCurrencyData(
    val currency: Currency,
    val currencyValue: Double,
)