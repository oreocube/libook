package com.oreocube.booksearch.data.repository

import com.oreocube.booksearch.data.database.FavoriteDao
import com.oreocube.booksearch.data.response.LibraryShortEntity
import com.oreocube.booksearch.data.response.toEntity
import com.oreocube.booksearch.domain.model.LibraryShort
import com.oreocube.booksearch.domain.repository.FavoriteRepository
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao,
) : FavoriteRepository {
    override suspend fun getFavoriteLibraries(): List<LibraryShort> {
        return favoriteDao.getFavoriteLibraries().map(LibraryShortEntity::toModel)
    }

    override suspend fun addFavoriteLibrary(library: LibraryShort) {
        favoriteDao.addFavoriteLibrary(library.toEntity())
    }

    override suspend fun deleteFavoriteLibrary(id: String) {
        favoriteDao.deleteFavoriteLibrary(id)
    }
}
