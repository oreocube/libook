package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.repository.UserRepository
import javax.inject.Inject

class UpdateFcmTokenUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(token: String) {
        val userId = userRepository.getUserId() ?: return
        userRepository.updateFcmToken(userId, token)
    }
}
