package com.oreocube.booksearch.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oreocube.booksearch.data.response.LibraryShortEntity

@Database(
    entities = [LibraryShortEntity::class],
    version = 1,
)
abstract class BookSearchDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}
