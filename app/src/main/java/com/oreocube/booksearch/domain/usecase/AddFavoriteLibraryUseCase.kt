package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.repository.FavoriteRepository
import javax.inject.Inject

class AddFavoriteLibraryUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
) {
    suspend operator fun invoke(libraryCode: Long) {
        favoriteRepository.addFavoriteLibrary(libraryCode)
    }
}
