package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.model.LibraryShort
import com.oreocube.booksearch.domain.repository.FavoriteRepository
import javax.inject.Inject

class AddFavoriteLibraryUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
) {
    suspend operator fun invoke(library: LibraryShort) {
        favoriteRepository.addFavoriteLibrary(library)
    }
}
