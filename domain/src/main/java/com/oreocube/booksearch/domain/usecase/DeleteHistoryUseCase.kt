package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.repository.HistoryRepository
import javax.inject.Inject

class DeleteHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository,
) {
    suspend operator fun invoke(isbn: String) {
        historyRepository.deleteByISBN(isbn = isbn)
    }
}
