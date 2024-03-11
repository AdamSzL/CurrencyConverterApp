package com.example.currencyconverterapp.watchlist.data.workers

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.watchlist.data.util.CHANNEL_ID
import com.example.currencyconverterapp.watchlist.data.util.NOTIFICATION_TITLE
import com.example.currencyconverterapp.watchlist.data.util.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
import com.example.currencyconverterapp.watchlist.data.util.VERBOSE_NOTIFICATION_CHANNEL_NAME

fun makeStatusNotification(
    context: Context,
    content: String,
    notificationId: Int,
) {
    val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
    val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
    val importance = NotificationManager.IMPORTANCE_HIGH
    val channel = NotificationChannel(CHANNEL_ID, name, importance)
    channel.description = description

    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

    notificationManager?.createNotificationChannel(channel)

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
    .setSmallIcon(R.drawable.ic_app_notifications)
        .setContentTitle(NOTIFICATION_TITLE)
        .setContentText(content)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))


    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return@with
        }
        notify(notificationId, builder.build())
    }
}