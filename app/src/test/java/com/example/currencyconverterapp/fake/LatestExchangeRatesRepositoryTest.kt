package com.example.currencyconverterapp.fake

import com.example.currencyconverterapp.watchlist.data.repository.LatestExchangeRatesRepositoryImpl
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class LatestExchangeRatesRepositoryTest {

    @Test
    fun latestExchangeRatesRepository_getLatestExchangeRates_verifyResponse() =
        runTest {
            val repository = LatestExchangeRatesRepositoryImpl(
                currencyConverterApiService = FakeCurrencyConverterApiService()
            )
            assertEquals(FakeDataSource.latestExchangeRatesApiResponse, repository.getLatestExchangeRates())
        }
}