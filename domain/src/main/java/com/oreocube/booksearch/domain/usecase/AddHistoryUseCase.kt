package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.model.RecentBookHistory
import com.oreocube.booksearch.domain.repository.HistoryRepository
import javax.inject.Inject

class AddHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository,
) {
    suspend operator fun invoke(item: RecentBookHistory) {
        historyRepository.add(item)
    }
}
