package com.oreocube.booksearch.domain.repository

import com.oreocube.booksearch.domain.model.param.BookNotificationTarget

interface NotificationRepository {
    suspend fun registerNotificationForBookStatus(uid: String, target: BookNotificationTarget)
    suspend fun isNotificationEnabled(uid: String, libraryId: String, isbn: String): Boolean
}
