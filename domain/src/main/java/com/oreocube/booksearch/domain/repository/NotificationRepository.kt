package com.oreocube.booksearch.domain.repository

import com.oreocube.booksearch.domain.model.param.BookNotificationTarget

interface NotificationRepository {
    suspend fun registerNotificationForBookStatus(uid: String, target: BookNotificationTarget)
    suspend fun isNotificationRegistered(uid: String, libraryId: String, isbn: String): Boolean
}
