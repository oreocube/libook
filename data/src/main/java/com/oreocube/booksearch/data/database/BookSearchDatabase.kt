package com.oreocube.booksearch.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.oreocube.booksearch.data.response.BookShortEntity
import com.oreocube.booksearch.data.response.LibraryShortEntity
import com.oreocube.booksearch.data.response.RecentBookHistoryEntity

@Database(
    entities = [
        LibraryShortEntity::class,
        RecentBookHistoryEntity::class,
        BookShortEntity::class,
    ],
    version = 3,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3)
    ],
    exportSchema = true,
)
abstract class BookSearchDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun favoriteBookDao(): FavoriteBookDao
    abstract fun recentBookHistoryDao(): RecentBookHistoryDao
}
