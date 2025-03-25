package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.model.BookAvailability
import com.oreocube.booksearch.domain.model.LibraryShort
import com.oreocube.booksearch.domain.model.param.BookAvailabilityCheckParam
import com.oreocube.booksearch.domain.repository.FavoriteRepository
import com.oreocube.booksearch.domain.repository.LibraryRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckBookAvailabilityUseCase @Inject constructor(
    private val libraryRepository: LibraryRepository,
    private val favoriteRepository: FavoriteRepository,
) {
    operator fun invoke(isbn: String): Flow<List<Pair<LibraryShort, BookAvailability>>> {
        return favoriteRepository.getFavoriteLibraries()
            .flatMapLatest { favoriteLibraries ->
                getBookAvailabilityForLibraries(isbn, favoriteLibraries)
            }
    }

    private fun getBookAvailabilityForLibraries(
        isbn: String,
        libraries: List<LibraryShort>
    ): Flow<List<Pair<LibraryShort, BookAvailability>>> = flow {
        val results = coroutineScope {
            libraries.map { library ->
                async {
                    val availability = libraryRepository.checkBookAvailability(
                        BookAvailabilityCheckParam(isbn = isbn, libraryCode = library.id)
                    )
                    library to availability
                }
            }.awaitAll()
        }
        emit(results)
    }
}
