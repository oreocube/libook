package com.oreocube.booksearch.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.toObject
import com.oreocube.booksearch.data.response.BookNotificationResponse
import com.oreocube.booksearch.data.response.NotificationSettingDto
import com.oreocube.booksearch.domain.model.param.BookNotificationTarget
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore,
) : NotificationDataSource {
    override suspend fun registerNotificationForBookStatus(
        uid: String,
        target: BookNotificationTarget,
    ) {
        val documentId = getDocumentId(uid, target.libraryId, target.isbn)
        val notificationSetting = with(target) {
            NotificationSettingDto(
                uid = uid,
                libraryId = libraryId,
                libraryName = libraryName,
                isbn = isbn,
                bookTitle = bookTitle,
            )
        }
        db.collection(NOTIFICATION_COLLECTION)
            .document(documentId)
            .set(notificationSetting)
            .await()
    }

    override suspend fun unregisterNotificationForBookStatus(
        uid: String,
        target: BookNotificationTarget,
    ) {
        val documentId = getDocumentId(uid, target.libraryId, target.isbn)
        db.collection(NOTIFICATION_COLLECTION)
            .document(documentId)
            .delete()
            .await()
    }

    override suspend fun isNotificationRegistered(
        uid: String,
        libraryId: String,
        isbn: String
    ): Boolean {
        val documentId = getDocumentId(uid, libraryId, isbn)
        return db.collection(NOTIFICATION_COLLECTION)
            .document(documentId)
            .get()
            .await()
            .exists()
    }

    override suspend fun getAllNotifications(uid: String): List<BookNotificationResponse> {
        return db.collection(NOTIFICATION_COLLECTION)
            .whereEqualTo("uid", uid)
            .get()
            .await()
            .map(QueryDocumentSnapshot::toObject)
    }

    private fun getDocumentId(uid: String, libraryId: String, isbn: String): String {
        return "${uid}_${libraryId}_${isbn}"
    }

    companion object {
        private const val NOTIFICATION_COLLECTION = "notifications"
    }
}
