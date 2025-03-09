package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.model.BookAvailability
import com.oreocube.booksearch.domain.model.LibraryShort
import com.oreocube.booksearch.domain.model.param.BookAvailabilityCheckParam
import com.oreocube.booksearch.domain.repository.FavoriteRepository
import com.oreocube.booksearch.domain.repository.LibraryRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class CheckBookAvailabilityUseCase @Inject constructor(
    private val libraryRepository: LibraryRepository,
    private val favoriteRepository: FavoriteRepository,
) {
    suspend operator fun invoke(isbn: String): List<Pair<LibraryShort, BookAvailability>> {
        return coroutineScope {
            favoriteRepository.getFavoriteLibraries().map { library ->
                async {
                    val result = libraryRepository.checkBookAvailability(
                        param = BookAvailabilityCheckParam(
                            isbn = isbn,
                            libraryCode = library.id,
                        )
                    )
                    Pair(library, result)
                }
            }.awaitAll()
        }
    }
}
