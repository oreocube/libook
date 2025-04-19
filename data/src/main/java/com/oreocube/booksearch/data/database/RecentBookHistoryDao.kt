package com.oreocube.booksearch.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oreocube.booksearch.data.response.RecentBookHistoryEntity

@Dao
interface RecentBookHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: RecentBookHistoryEntity)

    @Query("SELECT * FROM recent_book_history ORDER BY searchedAt DESC")
    suspend fun getAll(): List<RecentBookHistoryEntity>

    @Query("DELETE FROM recent_book_history WHERE isbn = :isbn")
    suspend fun deleteByISBN(isbn: String)

    @Query("DELETE FROM recent_book_history")
    suspend fun clear()
}
