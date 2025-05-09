package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.model.RecentBookHistory
import com.oreocube.booksearch.domain.repository.HistoryRepository
import javax.inject.Inject

class GetAllHistoriesUseCase @Inject constructor(
    private val historyRepository: HistoryRepository,
) {
    suspend operator fun invoke(): List<RecentBookHistory> {
        return historyRepository.getAll()
    }
}
