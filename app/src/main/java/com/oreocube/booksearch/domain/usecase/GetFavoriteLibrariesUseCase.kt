package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.model.LibraryShort
import com.oreocube.booksearch.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteLibrariesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
) {
    operator fun invoke(): Flow<List<LibraryShort>> {
        return favoriteRepository.getFavoriteLibraries()
    }
}
