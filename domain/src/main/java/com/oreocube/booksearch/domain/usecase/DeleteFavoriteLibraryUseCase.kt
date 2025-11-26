package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.repository.FavoriteLibraryRepository
import javax.inject.Inject

class DeleteFavoriteLibraryUseCase @Inject constructor(
    private val favoriteLibraryRepository: FavoriteLibraryRepository,
) {
    suspend operator fun invoke(id: String) {
        favoriteLibraryRepository.deleteFavoriteLibrary(id)
    }
}
