package com.oreocube.booksearch.data.repository

import com.oreocube.booksearch.domain.model.LibraryShort
import com.oreocube.booksearch.domain.repository.FavoriteRepository
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor() : FavoriteRepository {
    override suspend fun getFavoriteLibraries(): List<LibraryShort> {
        TODO("Not yet implemented")
    }

    override suspend fun addFavoriteLibrary(library: LibraryShort) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFavoriteLibrary(id: String) {
        TODO("Not yet implemented")
    }
}
