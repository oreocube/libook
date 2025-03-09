package com.oreocube.booksearch.domain.repository

import com.oreocube.booksearch.domain.model.LibraryShort

interface FavoriteRepository {
    suspend fun getFavoriteLibraries(): List<LibraryShort>
    suspend fun addFavoriteLibrary(library: LibraryShort)
    suspend fun deleteFavoriteLibrary(id: String)
}
