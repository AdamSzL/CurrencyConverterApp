package com.example.currencyconverterapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.data.CurrencyConverterRepository
import com.example.currencyconverterapp.ui.screens.converter.CurrenciesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val currencyConverterRepository: CurrencyConverterRepository
): ViewModel() {
    private val _currenciesUiState = MutableStateFlow<CurrenciesUiState>(CurrenciesUiState.Loading)
    val currenciesUiState: StateFlow<CurrenciesUiState> = _currenciesUiState

    init {
        fetchCurrencies()
    }

    fun restoreToLoadingState() {
        _currenciesUiState.value = CurrenciesUiState.Loading
    }

    fun fetchCurrencies() {
        viewModelScope.launch {
            _currenciesUiState.value = try {
                val newCurrencies = currencyConverterRepository.getCurrencies().data.values.toList()
                CurrenciesUiState.Success(newCurrencies)
            } catch (e: IOException) {
                CurrenciesUiState.Error
            }
        }
    }
}
