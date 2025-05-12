package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.model.RecommendedBook
import com.oreocube.booksearch.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRecommendedBooksWithTargetBookUseCase @Inject constructor(
    private val libraryRepository: LibraryRepository,
) {
    operator fun invoke(targetIsbn: String): Flow<List<RecommendedBook>> = flow {
        emit(libraryRepository.getRecommendedBooks(targetIsbn))
    }.catch {
        emit(emptyList())
    }
}
