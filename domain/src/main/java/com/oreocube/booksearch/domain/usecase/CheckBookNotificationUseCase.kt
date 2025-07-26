package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.repository.NotificationRepository
import com.oreocube.booksearch.domain.repository.UserRepository
import javax.inject.Inject

class CheckBookNotificationUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val notificationRepository: NotificationRepository,
) {
    suspend operator fun invoke(libraryId: String, isbn: String): Boolean {
        val uid = userRepository.getCurrentUserId() ?: return false
        return notificationRepository.isNotificationRegistered(uid, libraryId, isbn)
    }
}
