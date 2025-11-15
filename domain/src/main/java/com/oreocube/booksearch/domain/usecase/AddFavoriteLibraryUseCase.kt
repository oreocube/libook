package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.model.LibraryShort
import com.oreocube.booksearch.domain.repository.FavoriteLibraryRepository
import javax.inject.Inject

class AddFavoriteLibraryUseCase @Inject constructor(
    private val favoriteLibraryRepository: FavoriteLibraryRepository,
) {
    suspend operator fun invoke(library: LibraryShort) {
        favoriteLibraryRepository.addFavoriteLibrary(library)
    }
}
