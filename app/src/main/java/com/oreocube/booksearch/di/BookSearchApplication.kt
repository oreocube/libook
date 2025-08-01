package com.oreocube.booksearch.di

import android.app.Application
import com.oreocube.booksearch.notification.LiBookNotificationHandler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BookSearchApplication : Application() {
    @Inject
    lateinit var notificationHandler: LiBookNotificationHandler

    override fun onCreate() {
        super.onCreate()
        notificationHandler.createNotificationChannel()
    }
}
