package com.example.currencyconverterapp.converter.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.converter.data.repository.ConverterRepository
import com.example.currencyconverterapp.core.data.model.Currency
import com.example.currencyconverterapp.core.data.model.ExchangeRate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val converterRepository: ConverterRepository,
): ViewModel() {
    private val _converterUiState = MutableStateFlow(ConverterUiState())
    val converterUiState: StateFlow<ConverterUiState> = _converterUiState

    init {
        viewModelScope.launch {
            converterRepository.getLatestConverterDataStream().collect { converterData ->
                val shouldRefreshLatestExchangeRates = converterData.exchangeRates.any { it.value == null }
                _converterUiState.update {
                    it.copy(
                        baseCurrency = converterData.baseCurrency,
                        baseCurrencyValue = converterData.baseCurrencyValue,
                        exchangeRates = converterData.exchangeRates,
                        exchangeRatesUiState = ExchangeRatesUiState.Success,
                    )
                }
                if (shouldRefreshLatestExchangeRates) {
                    refreshLatestExchangeRatesAndHandleError(
                        baseCurrency = converterData.baseCurrency,
                        exchangeRates = converterData.exchangeRates
                    )
                }
            }
        }
    }

    fun refreshLatestExchangeRatesAndHandleError(
        baseCurrency: Currency,
        exchangeRates: List<ExchangeRate>
    ) {
        viewModelScope.launch {
            try {
                converterRepository.refreshLatestExchangeRates(
                    baseCurrency = baseCurrency,
                    exchangeRates = exchangeRates
                )
            } catch (e: IOException) {
                delay(200)
                _converterUiState.update {
                    it.copy(
                        exchangeRatesUiState = ExchangeRatesUiState.Error
                    )
                }
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

    private fun resetExchangeRates(exchangeRates: List<ExchangeRate>) {
        _converterUiState.update {
            it.copy(exchangeRates = exchangeRates)
        }
    }


    fun selectBaseCurrency(currency: Currency) {
        val updatedExchangeRates = converterUiState.value.exchangeRates
            .filter { exchangeRate ->
                exchangeRate.code != currency.code
            }
            .map { exchangeRate ->
                exchangeRate.copy(value = null)
            }
        resetExchangeRates(updatedExchangeRates)
        refreshLatestExchangeRatesAndHandleError(
            baseCurrency = currency,
            exchangeRates = updatedExchangeRates,
        )
    }

    fun setBaseCurrencyValue(value: Double) {
        viewModelScope.launch {
            converterRepository.updateCurrencyValue(value)
        }
    }

    fun selectTargetCurrency(currency: Currency) {
        _converterUiState.update {
            it.copy(selectedTargetCurrency = currency)
        }
    }

    private fun resetTargetCurrency() {
        _converterUiState.update {
            it.copy(selectedTargetCurrency = null)
        }
    }

    fun addTargetCurrency(
        baseCurrency: Currency,
        currency: Currency
    ) {
        val updatedExchangeRates = (converterUiState.value.exchangeRates + ExchangeRate(
                currency.code,
                null
            )).sortedBy { exchangeRate ->
                exchangeRate.code
            }
        resetExchangeRates(updatedExchangeRates)
        resetTargetCurrency()
        refreshLatestExchangeRatesAndHandleError(
            baseCurrency = baseCurrency,
            exchangeRates = updatedExchangeRates
        )
    }

    fun deleteConversionEntry(code: String) {
        val deletedExchangeRate = converterUiState.value.exchangeRates.find { exchangeRate ->
            exchangeRate.code == code
        }
        val updatedExchangeRates = converterUiState.value.exchangeRates
                .filter { exchangeRate ->
                    exchangeRate.code != code
                }
        _converterUiState.update {
            it.copy(
                exchangeRates = updatedExchangeRates,
                deletedExchangeRate = deletedExchangeRate,
            )
        }
        viewModelScope.launch {
            converterRepository.updateExchangeRates(updatedExchangeRates)
        }
    }

    fun undoConversionEntryDeletion() {
        with (converterUiState.value) {
            val updatedExchangeRates = if (deletedExchangeRate != null) {
                (exchangeRates + ExchangeRate(code = deletedExchangeRate.code, value = deletedExchangeRate.value))
                    .sortedBy { exchangeRate ->
                        exchangeRate.code
                    }
            } else {
                exchangeRates
            }
            updateExchangeRates(updatedExchangeRates)
        }
    }

    private fun updateExchangeRates(exchangeRates: List<ExchangeRate>) {
        viewModelScope.launch {
            converterRepository.updateExchangeRates(exchangeRates)
        }
    }
}