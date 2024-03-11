package com.example.currencyconverterapp.watchlist.data.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.converter.presentation.util.CurrencyUtils.getCurrencyFormat
import com.example.currencyconverterapp.watchlist.data.repository.LatestExchangeRatesRepository
import com.example.currencyconverterapp.watchlist.data.repository.WatchlistDataRepository
import com.example.currencyconverterapp.watchlist.data.util.WatchlistItemUtils.checkIfWatchlistItemConditionFulfilled
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

private const val TAG = "WatchlistWorker"

@HiltWorker
class WatchlistWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val watchlistDataRepository: WatchlistDataRepository,
    private val latestExchangeRatesRepository: LatestExchangeRatesRepository
): CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val watchlistItems = watchlistDataRepository.watchlistData.first().watchlistItems

                watchlistItems.forEachIndexed { index, watchlistItem ->
                    val latestExchangeRate = latestExchangeRatesRepository.getLatestExchangeRates(
                        baseCurrency = watchlistItem.baseCurrency.code,
                        currencies = watchlistItem.targetCurrency.code,
                    ).data.values.toList().first().value!!

                    val isWatchlistItemConditionFulfilled = checkIfWatchlistItemConditionFulfilled(
                        watchlistItem = watchlistItem,
                        latestExchangeRate = latestExchangeRate
                    )

                    if (isWatchlistItemConditionFulfilled) {
                        val baseCurrencyFormat = getCurrencyFormat(watchlistItem.baseCurrency)
                        val targetCurrencyFormat = getCurrencyFormat(watchlistItem.targetCurrency)
                        makeStatusNotification(
                            context = applicationContext,
                            content = applicationContext.resources.getString(
                                R.string.watchlist_item_notification_text,
                                baseCurrencyFormat.format(1),
                                targetCurrencyFormat.format(latestExchangeRate)
                            ),
                            notificationId = index,
                        )
                        watchlistDataRepository.removeWatchlistItem(watchlistItem.id)
                    }
                }
                Result.success()
            } catch (throwable: Throwable) {
                Result.failure()
            }
        }
    }
}