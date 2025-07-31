package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.model.param.BookNotificationTarget
import com.oreocube.booksearch.domain.repository.NotificationRepository
import com.oreocube.booksearch.domain.repository.UserRepository
import javax.inject.Inject

class UnregisterNotificationForBookStatusUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val notificationRepository: NotificationRepository,
) {
    suspend operator fun invoke(target: BookNotificationTarget) {
        val userId = userRepository.getCurrentUserId() ?: return
        notificationRepository.unregisterNotificationForBookStatus(
            uid = userId,
            target = target,
        )
    }
}
