package com.example.currencyconverterapp.fake

import com.example.currencyconverterapp.charts.data.repository.HistoricalExchangeRatesRepository
import com.example.currencyconverterapp.charts.data.repository.HistoricalExchangeRatesRepositoryImpl
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class HistoricalExchangeRatesRepositoryTest {

    @Test
    fun historicalExchangeRatesRepository_getHistoricalExchangeRates_verifyResponse() =
        runTest {
            val repository = HistoricalExchangeRatesRepositoryImpl(
                currencyConverterApiService = FakeCurrencyConverterApiService()
            )
            assertEquals(FakeDataSource.historicalExchangeRatesApiResponse, repository.getHistoricalExchangeRates())
        }
}