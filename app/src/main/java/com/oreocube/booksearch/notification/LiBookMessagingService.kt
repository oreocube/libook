package com.oreocube.booksearch.notification

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.oreocube.booksearch.domain.usecase.UpdateFcmTokenUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LiBookMessagingService : FirebaseMessagingService() {
    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)

    @Inject
    lateinit var updateFcmTokenUseCase: UpdateFcmTokenUseCase

    @Inject
    lateinit var notificationHandler: LiBookNotificationHandler

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        serviceScope.launch {
            updateFcmTokenUseCase(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        notificationHandler.sendNotification(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
    }
}
