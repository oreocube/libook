package com.oreocube.booksearch.di

import com.oreocube.booksearch.data.repository.FavoriteRepositoryImpl
import com.oreocube.booksearch.data.repository.LibraryRepositoryImpl
import com.oreocube.booksearch.data.repository.RegionRepositoryImpl
import com.oreocube.booksearch.domain.repository.FavoriteRepository
import com.oreocube.booksearch.domain.repository.LibraryRepository
import com.oreocube.booksearch.domain.repository.RegionRepository
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
    fun bindsFavoriteRepository(repositoryImpl: FavoriteRepositoryImpl): FavoriteRepository
}
