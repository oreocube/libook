package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.repository.FavoriteRepository
import javax.inject.Inject

class DeleteFavoriteLibraryUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
) {
    suspend operator fun invoke(id: String) {
        favoriteRepository.deleteFavoriteLibrary(id)
    }
}
