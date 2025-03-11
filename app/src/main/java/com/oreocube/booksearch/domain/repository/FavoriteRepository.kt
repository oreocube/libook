package com.oreocube.booksearch.domain.repository

import com.oreocube.booksearch.domain.model.LibraryShort
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavoriteLibraries(): Flow<List<LibraryShort>>
    suspend fun addFavoriteLibrary(library: LibraryShort)
    suspend fun deleteFavoriteLibrary(id: String)
}
