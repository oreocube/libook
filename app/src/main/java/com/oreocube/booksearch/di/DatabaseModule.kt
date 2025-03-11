package com.oreocube.booksearch.di

import android.content.Context
import androidx.room.Room
import com.oreocube.booksearch.data.database.BookSearchDatabase
import com.oreocube.booksearch.data.database.FavoriteDao
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
    fun providesNiaDatabase(
        @ApplicationContext context: Context,
    ): BookSearchDatabase = Room.databaseBuilder(
        context,
        BookSearchDatabase::class.java,
        "book-search-database",
    ).build()

    @Provides
    @Singleton
    fun providesFavoriteDao(
        database: BookSearchDatabase,
    ): FavoriteDao = database.favoriteDao()
}
