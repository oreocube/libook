package com.oreocube.booksearch.data.datasource

import com.oreocube.booksearch.domain.model.param.BookNotificationTarget

interface NotificationDataSource {
    suspend fun registerNotificationForBookStatus(uid: String, target: BookNotificationTarget)
    suspend fun isNotificationEnabled(uid: String, libraryId: String, isbn: String): Boolean
}
