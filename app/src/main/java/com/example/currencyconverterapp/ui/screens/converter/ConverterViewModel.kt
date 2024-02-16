package com.example.currencyconverterapp.ui.screens.converter

//import com.example.currencyconverterapp.data.ConverterCachedDataRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.data.ConverterCachedDataRepository
import com.example.currencyconverterapp.data.CurrencyConverterRepository
import com.example.currencyconverterapp.model.Currency
import com.example.currencyconverterapp.model.ExchangeRate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val currencyConverterRepository: CurrencyConverterRepository,
    private val converterCachedDataRepository: ConverterCachedDataRepository,
): ViewModel() {
    private val _converterUiState = MutableStateFlow(ConverterUiState())
    val converterUiState: StateFlow<ConverterUiState> = _converterUiState

    init {
        viewModelScope.launch {
            converterCachedDataRepository.savedConverterData.first { converterCachedData ->
                _converterUiState.update {
                    it.copy(
                        baseCurrency = converterCachedData.baseCurrency,
                        baseCurrencyValue = converterCachedData.baseCurrencyValue,
                        exchangeRatesUiState = ExchangeRatesUiState.Success(converterCachedData.exchangeRates)
                    )
                }
                fetchExchangeRates(
                    converterCachedData.baseCurrency,
                    converterCachedData.exchangeRates
                )
                true
            }
        }
    }

    private fun fetchExchangeRates(
        baseCurrency: Currency,
        exchangeRates: List<ExchangeRate>
    ) {
        viewModelScope.launch {
            val exchangeRatesUiState = try {
                val latestExchangeRates = currencyConverterRepository.getLatestExchangeRates(
                    baseCurrency = baseCurrency.code,
                    currencies = exchangeRates.joinToString(",") { it.code },
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
            exchangeRates = updatedExchangeRates,
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
        fetchExchangeRates(
            baseCurrency = baseCurrency,
            exchangeRates = updatedExchangeRates
        )
        updateSavedExchangeRates(updatedExchangeRates)
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
        updateSavedExchangeRates(updatedExchangeRates)
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