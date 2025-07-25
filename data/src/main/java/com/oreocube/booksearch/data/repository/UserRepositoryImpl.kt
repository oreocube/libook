package com.oreocube.booksearch.data.repository

import com.oreocube.booksearch.data.datasource.UserDataSource
import com.oreocube.booksearch.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository {
    override suspend fun authenticateAnonymously() {
        userDataSource.authenticateAnonymously()
    }

    override suspend fun getUserId(): String? {
        return userDataSource.getUserId()
    }

    override suspend fun getFcmToken(): String? {
        return userDataSource.getFcmToken()
    }

    override suspend fun updateFcmToken(uid: String, token: String) {
        return userDataSource.updateFcmToken(uid, token)
    }
}
