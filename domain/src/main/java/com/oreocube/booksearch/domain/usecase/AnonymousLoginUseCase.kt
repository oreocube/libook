package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.repository.UserRepository
import javax.inject.Inject

class AnonymousLoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke() {
        userRepository.authenticateAnonymously()
    }
}
