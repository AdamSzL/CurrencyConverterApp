package com.example.currencyconverterapp

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.currencyconverterapp.ui.CurrencyConverterApp
import com.example.currencyconverterapp.ui.screens.CurrenciesViewModel
import com.example.currencyconverterapp.ui.screens.charts.ChartsViewModel
import com.example.currencyconverterapp.ui.screens.charts.HistoricalExchangeRatesUiState
import com.example.currencyconverterapp.ui.screens.converter.ConverterViewModel
import com.example.currencyconverterapp.ui.screens.converter.CurrenciesUiState
import com.example.currencyconverterapp.ui.screens.converter.ExchangeRatesUiState
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val currenciesViewModel: CurrenciesViewModel by viewModels()
    private val converterViewModel: ConverterViewModel by viewModels()
    private val chartsViewModel: ChartsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepVisibleCondition {
                currenciesViewModel.currenciesUiState.value == CurrenciesUiState.Loading
                        || converterViewModel.converterUiState.value.exchangeRatesUiState == ExchangeRatesUiState.Loading
                        || chartsViewModel.chartsUiState.value.historicalExchangeRatesUiState == HistoricalExchangeRatesUiState.Loading
            }
            setOnExitAnimationListener { screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    0.6f,
                    0.0f
                )
                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = 500L
                zoomX.doOnEnd { screen.remove() }

                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    0.6f,
                    0.0f
                )
                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 500L
                zoomY.doOnEnd { screen.remove() }

                zoomX.start()
                zoomY.start()
            }
        }
        setContent {
            CurrencyConverterAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CurrencyConverterApp()
                }
            }
        }
    }
}