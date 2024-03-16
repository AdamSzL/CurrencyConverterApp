package com.example.currencyconverterapp.fake

//import com.example.currencyconverterapp.data.repository.NetworkCurrencyConverterRepository
//import kotlinx.coroutines.test.runTest
//import org.junit.Assert.assertEquals
//import org.junit.Test
//
//class NetworkCurrencyConverterRepositoryTest {
//
//    private val repository = NetworkCurrencyConverterRepository(
//        currencyConverterApiService = FakeCurrencyConverterApiService()
//    )
//
//    @Test
//    fun networkCurrencyConverterRepository_getCurrencies_verifyApiResponse() =
//        runTest {
//            assertEquals(FakeDataSource.currenciesApiResponse, repository.getCurrencies())
//        }
//
//    @Test
//    fun networkCurrencyConverterRepository_getLatestExchangeRates_verifyApiResponse() =
//        runTest {
//            assertEquals(FakeDataSource.latestExchangeRatesApiResponse, repository.getLatestExchangeRates())
//        }
//
//    @Test
//    fun networkCurrencyConverterRepository_getHistoricalExchangeRates_verifyApiResponse() =
//        runTest {
//            assertEquals(FakeDataSource.historicalExchangeRatesApiResponse, repository.getHistoricalExchangeRates())
//        }
//}