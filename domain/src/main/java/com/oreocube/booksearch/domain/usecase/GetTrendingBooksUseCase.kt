package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.model.TrendingBook
import com.oreocube.booksearch.domain.repository.LibraryRepository
import java.time.LocalDate
import javax.inject.Inject

class GetTrendingBooksUseCase @Inject constructor(
    private val libraryRepository: LibraryRepository
) {
    suspend operator fun invoke(): List<TrendingBook> {
        val targetDate = LocalDate.now().minusDays(1)
        return libraryRepository.getTrendingBooks(targetDate)
    }
}
