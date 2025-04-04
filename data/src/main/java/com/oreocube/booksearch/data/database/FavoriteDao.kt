package com.oreocube.booksearch.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oreocube.booksearch.data.response.LibraryShortEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM library")
    fun getFavoriteLibraries(): Flow<List<LibraryShortEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteLibrary(library: LibraryShortEntity)

    @Query("DELETE FROM library WHERE id = :id")
    suspend fun deleteFavoriteLibrary(id: String)
}
