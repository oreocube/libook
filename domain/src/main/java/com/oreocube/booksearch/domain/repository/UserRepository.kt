package com.oreocube.booksearch.domain.repository

interface UserRepository {
    suspend fun authenticateAnonymously()
    suspend fun getCurrentUserId(): String?
    suspend fun getFcmToken(): String?
    suspend fun updateFcmToken(uid: String, token: String)
}
