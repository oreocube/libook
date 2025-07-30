package com.oreocube.booksearch.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import com.oreocube.booksearch.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LiBookNotificationHandler @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val notificationManager by lazy { NotificationManagerCompat.from(context) }

    fun createNotificationChannel() {
        val channelId = context.getString(R.string.default_notification_channel_id)
        val name = context.getString(R.string.default_channel_name)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, name, importance)

        notificationManager.createNotificationChannel(channel)
    }
}
