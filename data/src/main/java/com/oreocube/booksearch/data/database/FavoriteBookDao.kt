package com.oreocube.booksearch.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oreocube.booksearch.data.response.BookShortEntity

@Dao
interface FavoriteBookDao {
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_books WHERE isbn = :isbn)")
    suspend fun existsByIsbn(isbn: String): Boolean

    @Query("SELECT * FROM favorite_books")
    suspend fun getFavoriteBooks(): List<BookShortEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteBook(book: BookShortEntity)

    @Query("DELETE FROM favorite_books WHERE isbn = :isbn")
    suspend fun deleteFavoriteBook(isbn: String)
}
