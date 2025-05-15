package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.model.RecommendedBook
import com.oreocube.booksearch.domain.repository.LibraryRepository
import javax.inject.Inject

class GetRecommendedBooksWithTargetBookUseCase @Inject constructor(
    private val libraryRepository: LibraryRepository,
) {
    suspend operator fun invoke(targetIsbn: String): List<RecommendedBook> {
        return libraryRepository.getRecommendedBooks(targetIsbn)
    }
}
