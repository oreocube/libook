package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.model.Library
import com.oreocube.booksearch.domain.model.param.LibrarySearchParam
import com.oreocube.booksearch.domain.repository.LibraryRepository
import javax.inject.Inject

class GetLibrariesByRegionUseCase @Inject constructor(
    private val libraryRepository: LibraryRepository,
) {
    suspend operator fun invoke(param: LibrarySearchParam): List<Library> {
        return libraryRepository.searchLibrariesByRegion(param)
    }
}
