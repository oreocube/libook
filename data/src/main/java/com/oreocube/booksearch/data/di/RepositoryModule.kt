package com.oreocube.booksearch.data.di

import com.oreocube.booksearch.data.repository.FavoriteLibraryLibraryRepositoryImpl
import com.oreocube.booksearch.data.repository.HistoryRepositoryImpl
import com.oreocube.booksearch.data.repository.LibraryRepositoryImpl
import com.oreocube.booksearch.data.repository.NotificationRepositoryImpl
import com.oreocube.booksearch.data.repository.RegionRepositoryImpl
import com.oreocube.booksearch.data.repository.UserRepositoryImpl
import com.oreocube.booksearch.domain.repository.FavoriteLibraryRepository
import com.oreocube.booksearch.domain.repository.HistoryRepository
import com.oreocube.booksearch.domain.repository.LibraryRepository
import com.oreocube.booksearch.domain.repository.NotificationRepository
import com.oreocube.booksearch.domain.repository.RegionRepository
import com.oreocube.booksearch.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsRegionRepository(repositoryImpl: RegionRepositoryImpl): RegionRepository

    @Binds
    fun bindsLibraryRepository(repositoryImpl: LibraryRepositoryImpl): LibraryRepository

    @Binds
    fun bindsFavoriteLibraryRepository(repositoryImpl: FavoriteLibraryLibraryRepositoryImpl): FavoriteLibraryRepository

    @Binds
    fun bindsHistoryRepository(repositoryImpl: HistoryRepositoryImpl): HistoryRepository

    @Binds
    fun bindsUserRepository(repositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    fun bindsNotificationRepository(repositoryImpl: NotificationRepositoryImpl): NotificationRepository
}
