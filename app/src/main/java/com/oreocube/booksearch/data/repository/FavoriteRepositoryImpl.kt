package com.oreocube.booksearch.data.repository

import com.oreocube.booksearch.domain.repository.FavoriteRepository

class FavoriteRepositoryImpl : FavoriteRepository {
    override suspend fun getFavoriteLibraries(): List<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun addFavoriteLibrary(libraryCode: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFavoriteLibrary(libraryCode: Long) {
        TODO("Not yet implemented")
    }
}
