package com.oreocube.booksearch.data.repository

import com.oreocube.booksearch.data.database.FavoriteLibraryDao
import com.oreocube.booksearch.data.response.LibraryShortEntity
import com.oreocube.booksearch.data.response.toEntity
import com.oreocube.booksearch.domain.model.LibraryShort
import com.oreocube.booksearch.domain.repository.FavoriteLibraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteLibraryLibraryRepositoryImpl @Inject constructor(
    private val favoriteLibraryDao: FavoriteLibraryDao,
) : FavoriteLibraryRepository {
    override fun getFavoriteLibraries(): Flow<List<LibraryShort>> {
        return favoriteLibraryDao.getFavoriteLibraries()
            .map { list -> list.map(LibraryShortEntity::toModel) }
    }

    override suspend fun addFavoriteLibrary(library: LibraryShort) {
        favoriteLibraryDao.addFavoriteLibrary(library.toEntity())
    }

    override suspend fun deleteFavoriteLibrary(id: String) {
        favoriteLibraryDao.deleteFavoriteLibrary(id)
    }
}
