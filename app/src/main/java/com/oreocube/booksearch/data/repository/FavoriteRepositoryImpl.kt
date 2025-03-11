package com.oreocube.booksearch.data.repository

import com.oreocube.booksearch.data.database.FavoriteDao
import com.oreocube.booksearch.data.response.LibraryShortEntity
import com.oreocube.booksearch.data.response.toEntity
import com.oreocube.booksearch.domain.model.LibraryShort
import com.oreocube.booksearch.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao,
) : FavoriteRepository {
    override fun getFavoriteLibraries(): Flow<List<LibraryShort>> {
        return favoriteDao.getFavoriteLibraries()
            .map { list -> list.map(LibraryShortEntity::toModel) }
    }

    override suspend fun addFavoriteLibrary(library: LibraryShort) {
        favoriteDao.addFavoriteLibrary(library.toEntity())
    }

    override suspend fun deleteFavoriteLibrary(id: String) {
        favoriteDao.deleteFavoriteLibrary(id)
    }
}
