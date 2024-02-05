package com.example.currencyconverterapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.data.CurrencyConverterRepository
import com.example.currencyconverterapp.data.defaultAvailableCurrencies
import com.example.currencyconverterapp.model.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val currencyConverterRepository: CurrencyConverterRepository
): ViewModel() {
    private val _currencies = MutableStateFlow(defaultAvailableCurrencies)
    val currencies: StateFlow<List<Currency>> = _currencies

    init {
        fetchCurrencies()
    }

    fun fetchCurrencies() {
        viewModelScope.launch {
            val newCurrencies = currencyConverterRepository.getCurrencies().data.values.toList()
            _currencies.value = newCurrencies
        }
    }
}
