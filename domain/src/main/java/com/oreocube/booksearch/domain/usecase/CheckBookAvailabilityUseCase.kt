package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.model.LibraryShort
import com.oreocube.booksearch.domain.model.LibraryWithAvailability
import com.oreocube.booksearch.domain.model.param.BookAvailabilityCheckParam
import com.oreocube.booksearch.domain.repository.LibraryRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

class CheckBookAvailabilityUseCase @Inject constructor(
    private val libraryRepository: LibraryRepository,
) {
    suspend operator fun invoke(
        isbn: String,
        libraries: List<LibraryShort>,
    ): List<LibraryWithAvailability> {
        return supervisorScope {
            libraries.map { library ->
                async { checkAvailabilitySafely(isbn, library) }
            }.awaitAll()
        }
    }

    suspend operator fun invoke(
        isbn: String,
        library: LibraryShort,
    ): LibraryWithAvailability = checkAvailabilitySafely(isbn, library)

    private suspend fun checkAvailabilitySafely(
        isbn: String,
        library: LibraryShort,
    ): LibraryWithAvailability {
        val availability = runCatching {
            libraryRepository.checkBookAvailability(
                BookAvailabilityCheckParam(isbn = isbn, libraryCode = library.id)
            )
        }
        return LibraryWithAvailability(
            library = library,
            availability = availability
        )
    }
}
