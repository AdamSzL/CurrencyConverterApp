package com.example.currencyconverterapp

import android.app.Application
import com.example.currencyconverterapp.data.AppContainer
import com.example.currencyconverterapp.data.DefaultAppContainer

class CurrencyConverterApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}