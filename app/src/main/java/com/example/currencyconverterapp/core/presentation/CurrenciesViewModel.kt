package com.example.currencyconverterapp.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.core.data.repository.CurrenciesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val currenciesRepository: CurrenciesRepository,
): ViewModel() {
    private val _currenciesUiState = MutableStateFlow<CurrenciesUiState>(CurrenciesUiState.Loading)
    val currenciesUiState = _currenciesUiState.asStateFlow()

    init {
        viewModelScope.launch {
            currenciesRepository.getCurrenciesStream().collect { currencies ->
                var newCurrenciesUiState: CurrenciesUiState = CurrenciesUiState.Success(currencies)
                if (currencies.isEmpty()) {
                    refreshCurrencies()
                    newCurrenciesUiState = CurrenciesUiState.Loading
                }
                _currenciesUiState.update {
                    newCurrenciesUiState
                }
            }
        }
    }

    private fun refreshCurrencies() {
        viewModelScope.launch {
            try {
                currenciesRepository.refreshCurrencies()
            } catch (e: Exception) {
                delay(200)
                _currenciesUiState.update {
                    CurrenciesUiState.Error
                }
            }
        }
    }

    fun restoreToLoadingStateAndRefreshCurrencies() {
        _currenciesUiState.update {
            CurrenciesUiState.Loading
        }
        refreshCurrencies()
    }
}