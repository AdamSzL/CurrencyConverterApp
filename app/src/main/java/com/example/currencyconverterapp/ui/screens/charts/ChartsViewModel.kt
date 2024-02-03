package com.example.currencyconverterapp.ui.screens.charts

import androidx.lifecycle.ViewModel
import com.example.currencyconverterapp.data.CurrencyConverterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChartsViewModel @Inject constructor(
    private val currencyConverterRepository: CurrencyConverterRepository
): ViewModel() {

}
