package com.example.currencyconverterapp.watchlist.presentation.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.currencyconverterapp.watchlist.presentation.notifications.NotificationsPermissionState

object NotificationUtils {

    fun getNotificationsPermissionState(
        context: Context
    ): NotificationsPermissionState {
        return when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                NotificationsPermissionState.PERMISSION_GRANTED
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity,
                Manifest.permission.POST_NOTIFICATIONS
            ) -> {
                NotificationsPermissionState.SHOW_RATIONALE
            }
            else -> {
                NotificationsPermissionState.ASK_FOR_PERMISSION
            }
        }
    }

}