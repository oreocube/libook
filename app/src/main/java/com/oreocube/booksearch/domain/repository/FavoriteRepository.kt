package com.oreocube.booksearch.domain.repository

interface FavoriteRepository {
    suspend fun getFavoriteLibraries(): List<Long>
    suspend fun addFavoriteLibrary(libraryCode: Long)
    suspend fun deleteFavoriteLibrary(libraryCode: Long)
}
