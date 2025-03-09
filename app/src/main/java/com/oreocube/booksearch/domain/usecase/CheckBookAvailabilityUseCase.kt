package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.model.BookAvailability
import com.oreocube.booksearch.domain.model.param.BookAvailabilityCheckParam
import com.oreocube.booksearch.domain.repository.LibraryRepository
import javax.inject.Inject

class CheckBookAvailabilityUseCase @Inject constructor(
    private val libraryRepository: LibraryRepository,
) {
    suspend operator fun invoke(parameter: BookAvailabilityCheckParam): BookAvailability {
        return libraryRepository.checkBookAvailability(parameter)
    }
}
