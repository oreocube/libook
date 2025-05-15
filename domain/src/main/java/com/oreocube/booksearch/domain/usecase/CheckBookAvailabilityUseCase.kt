package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.model.BookAvailability
import com.oreocube.booksearch.domain.model.LibraryShort
import com.oreocube.booksearch.domain.model.param.BookAvailabilityCheckParam
import com.oreocube.booksearch.domain.repository.FavoriteRepository
import com.oreocube.booksearch.domain.repository.LibraryRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CheckBookAvailabilityUseCase @Inject constructor(
    private val libraryRepository: LibraryRepository,
    private val favoriteRepository: FavoriteRepository,
) {
    suspend operator fun invoke(isbn: String): List<Pair<LibraryShort, BookAvailability>> {
        val libraries = favoriteRepository.getFavoriteLibraries().first()
        return coroutineScope {
            libraries.map { library ->
                async {
                    val availability = libraryRepository.checkBookAvailability(
                        BookAvailabilityCheckParam(isbn = isbn, libraryCode = library.id)
                    )
                    library to availability
                }
            }.awaitAll()
        }
    }
}
