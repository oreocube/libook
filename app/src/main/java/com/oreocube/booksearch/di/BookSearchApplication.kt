package com.oreocube.booksearch.di

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.oreocube.booksearch.data.worker.UpdateRecentHistoryWorker
import com.oreocube.booksearch.notification.LiBookNotificationHandler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BookSearchApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var notificationHandler: LiBookNotificationHandler

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        notificationHandler.createNotificationChannel()
        enqueueUpdateRecentHistoryWorker()
    }

    private fun enqueueUpdateRecentHistoryWorker() {
        val request = OneTimeWorkRequestBuilder<UpdateRecentHistoryWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(this).enqueueUniqueWork(
            "update_recent_history",
            ExistingWorkPolicy.KEEP,
            request
        )
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
