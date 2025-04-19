package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.repository.HistoryRepository
import javax.inject.Inject

class ClearHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository,
) {
    suspend operator fun invoke() {
        historyRepository.deleteAll()
    }
}
