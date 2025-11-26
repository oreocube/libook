package com.oreocube.booksearch.data.di

import android.content.Context
import androidx.room.Room
import com.oreocube.booksearch.data.database.BookSearchDatabase
import com.oreocube.booksearch.data.database.FavoriteBookDao
import com.oreocube.booksearch.data.database.FavoriteLibraryDao
import com.oreocube.booksearch.data.database.RecentBookHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun providesLiBookDatabase(
        @ApplicationContext context: Context,
    ): BookSearchDatabase = Room.databaseBuilder(
        context,
        BookSearchDatabase::class.java,
        "book-search-database",
    ).build()

    @Provides
    @Singleton
    fun providesFavoriteLibraryDao(
        database: BookSearchDatabase,
    ): FavoriteLibraryDao = database.favoriteLibraryDao()

    @Provides
    @Singleton
    fun providesFavoriteBookDao(
        database: BookSearchDatabase,
    ): FavoriteBookDao = database.favoriteBookDao()

    @Provides
    @Singleton
    fun providesHistoryDao(
        database: BookSearchDatabase,
    ): RecentBookHistoryDao = database.recentBookHistoryDao()
}
