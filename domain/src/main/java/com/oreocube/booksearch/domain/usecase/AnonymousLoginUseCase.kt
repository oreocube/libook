package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.repository.UserRepository
import javax.inject.Inject

class AnonymousLoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val updateFcmTokenUseCase: UpdateFcmTokenUseCase,
) {
    suspend operator fun invoke() {
        runCatching {
            userRepository.authenticateAnonymously()
        }.onSuccess {
            updateFcmTokenUseCase()
        }.getOrThrow()
    }
}
