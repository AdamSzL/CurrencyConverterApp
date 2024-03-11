package com.example.currencyconverterapp.watchlist.data.repository

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.currencyconverterapp.watchlist.data.util.WATCHLIST_WORK_NAME
import com.example.currencyconverterapp.watchlist.data.workers.WatchlistWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface WatchlistWorkManagerRepository {

    fun startWatchlistWork()

    fun cancelWatchlistWork()
}

class WatchlistWorkManagerRepositoryImpl @Inject constructor(
    private val workManager: WorkManager
): WatchlistWorkManagerRepository {

    private val workRequest =
        PeriodicWorkRequestBuilder<WatchlistWorker>(INTERVAL_MINUTES, TimeUnit.MINUTES).build()

    override fun startWatchlistWork() {
        workManager.enqueueUniquePeriodicWork(
            WATCHLIST_WORK_NAME,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            workRequest
        )
    }

    override fun cancelWatchlistWork() {
        workManager.cancelAllWork()
    }

    companion object {
        private const val INTERVAL_MINUTES = 15L
    }
}