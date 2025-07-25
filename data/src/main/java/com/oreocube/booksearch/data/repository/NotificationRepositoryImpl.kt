package com.oreocube.booksearch.data.repository

import com.oreocube.booksearch.data.datasource.NotificationDataSource
import com.oreocube.booksearch.domain.model.param.BookNotificationTarget
import com.oreocube.booksearch.domain.repository.NotificationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSource: NotificationDataSource,
) : NotificationRepository {

    override suspend fun registerNotificationForBookStatus(
        uid: String,
        target: BookNotificationTarget,
    ) {
        notificationDataSource.registerNotificationForBookStatus(uid, target)
    }

    override suspend fun isNotificationEnabled(
        uid: String,
        libraryId: String,
        isbn: String
    ): Boolean {
        return notificationDataSource.isNotificationEnabled(uid, libraryId, isbn)
    }
}
