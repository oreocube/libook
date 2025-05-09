package com.oreocube.booksearch.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.oreocube.booksearch.data.response.LibraryShortEntity
import com.oreocube.booksearch.data.response.RecentBookHistoryEntity

@Database(
    entities = [
        LibraryShortEntity::class,
        RecentBookHistoryEntity::class,
    ],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
    ],
    exportSchema = true,
)
abstract class BookSearchDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun recentBookHistoryDao(): RecentBookHistoryDao
}
