package com.example.currencyconverterapp.converter.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val converterRepository: ConverterRepository,
): ViewModel() {
    private val _converterUiState = MutableStateFlow(ConverterUiState())
    val converterUiState: StateFlow<ConverterUiState> = _converterUiState

    var currencyValue by mutableStateOf("1.00")
        private set

    init {
        viewModelScope.launch {
            converterRepository.getLatestConverterDataStream().collect { converterData ->
                val shouldRefreshLatestExchangeRates = converterData.exchangeRates.any { it.value == null }
                _converterUiState.update {
                    it.copy(
                        baseCurrency = converterData.baseCurrency,
                        exchangeRates = converterData.exchangeRates,
                        exchangeRatesUiState = ExchangeRatesUiState.Success,
                    )
                }
                currencyValue = converterData.baseCurrencyValue
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
            val snackbarMessage: String = try {
                converterRepository.refreshLatestExchangeRates(
                    baseCurrency = baseCurrency,
                    exchangeRates = exchangeRates
                )
                EXCHANGE_RATES_UPDATED_MESSAGE
            } catch (e: Exception) {
                delay(REQUEST_ERROR_DELAY)
                _converterUiState.update {
                    it.copy(
                        exchangeRatesUiState = ExchangeRatesUiState.Error
                    )
                }
                EXCHANGE_RATES_ERROR_FETCHING
            }
            _converterUiState.update {
                it.copy(
                    snackbarMessage = snackbarMessage
                )
            }
        }
    }

    fun showSnackbar(message: String) {
        _converterUiState.update {
            it.copy(
                snackbarMessage = message
            )
        }
    }

    fun updateCurrencyValue(newCurrencyValue: String) {
        currencyValue = newCurrencyValue
        viewModelScope.launch {
            converterRepository.updateCurrencyValue(newCurrencyValue)
        }
    }

    fun restoreToSuccessState() {
        _converterUiState.update {
            it.copy(
                exchangeRatesUiState = ExchangeRatesUiState.Success,
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
        if (updatedExchangeRates.isNotEmpty()) {
            refreshLatestExchangeRatesAndHandleError(
                baseCurrency = currency,
                exchangeRates = updatedExchangeRates,
            )
        } else {
            _converterUiState.update {
                it.copy(baseCurrency = currency)
            }
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

    companion object {

        private const val REQUEST_ERROR_DELAY = 200L
        private const val EXCHANGE_RATES_UPDATED_MESSAGE = "Exchange rates have been successfully updated"
        private const val EXCHANGE_RATES_ERROR_FETCHING = "Error while fetching latest exchange rates"

    }
}