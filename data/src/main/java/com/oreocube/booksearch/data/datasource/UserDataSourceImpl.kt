package com.oreocube.booksearch.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val messaging: FirebaseMessaging,
) : UserDataSource {
    override suspend fun authenticateAnonymously() {
        auth.signInAnonymously().await()
    }

    override suspend fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    override suspend fun getFcmToken(): String? {
        return messaging.token.await()
    }

    override suspend fun updateFcmToken(uid: String, token: String) {
        db.collection("users")
            .document(uid)
            .set(
                mapOf(
                    "fcmToken" to token,
                    "lastUpdated" to System.currentTimeMillis(),
                )
            )
            .await()
    }
}
