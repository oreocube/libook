package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.model.LibraryShort
import com.oreocube.booksearch.domain.repository.FavoriteLibraryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteLibrariesUseCase @Inject constructor(
    private val favoriteLibraryRepository: FavoriteLibraryRepository,
) {
    operator fun invoke(): Flow<List<LibraryShort>> {
        return favoriteLibraryRepository.getFavoriteLibraries()
    }
}
