package com.oreocube.booksearch.domain.repository

import com.oreocube.booksearch.domain.model.param.BookNotificationTarget

interface NotificationRepository {
    suspend fun registerNotificationForBookStatus(uid: String, target: BookNotificationTarget)
    suspend fun unregisterNotificationForBookStatus(uid: String, target: BookNotificationTarget)
    suspend fun isNotificationRegistered(uid: String, libraryId: String, isbn: String): Boolean
    suspend fun getAllNotifications(uid: String): List<BookNotificationTarget>
}
