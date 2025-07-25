package com.oreocube.booksearch.data.datasource

interface UserDataSource {
    suspend fun authenticateAnonymously()
    suspend fun getUserId(): String?
    suspend fun getFcmToken(): String?
    suspend fun updateFcmToken(uid: String, token: String)
}
