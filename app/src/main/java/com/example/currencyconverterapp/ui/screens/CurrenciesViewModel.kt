package com.example.currencyconverterapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.data.CurrenciesCachedDataRepository
import com.example.currencyconverterapp.data.CurrencyConverterRepository
import com.example.currencyconverterapp.ui.screens.converter.CurrenciesUiState
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
class CurrenciesViewModel @Inject constructor(
    private val currencyConverterRepository: CurrencyConverterRepository,
    private val currenciesCachedDataRepository: CurrenciesCachedDataRepository
): ViewModel() {
    private val _currenciesUiState = MutableStateFlow<CurrenciesUiState>(CurrenciesUiState.Loading)
    val currenciesUiState: StateFlow<CurrenciesUiState> = _currenciesUiState

    init {
        viewModelScope.launch {
            currenciesCachedDataRepository.savedCurrencies.first { savedCurrenciesData ->
                _currenciesUiState.update {
                    if (savedCurrenciesData.currencies.isNotEmpty()) {
                        return@update CurrenciesUiState.Success(savedCurrenciesData.currencies)
                    }
                    fetchCurrencies()
                    CurrenciesUiState.Loading
                }
                true
            }
        }
    }

    fun restoreToLoadingStateAndFetchCurrencies() {
        _currenciesUiState.update {
            CurrenciesUiState.Loading
        }
        fetchCurrencies()
    }

    private fun fetchCurrencies() {
        viewModelScope.launch {
            val result = try {
                val newCurrencies = currencyConverterRepository.getCurrencies().data.values.toList()
                currenciesCachedDataRepository.updateSavedCurrencies(newCurrencies)
                CurrenciesUiState.Success(newCurrencies)
            } catch (e: IOException) {
                delay(200)
                CurrenciesUiState.Error
            }
            _currenciesUiState.update {
                result
            }
        }
    }
}
