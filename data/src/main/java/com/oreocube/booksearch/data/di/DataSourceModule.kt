package com.oreocube.booksearch.data.di

import com.oreocube.booksearch.data.datasource.RegionDataSource
import com.oreocube.booksearch.data.datasource.RegionDataSourceImpl
import com.oreocube.booksearch.data.datasource.UserDataSource
import com.oreocube.booksearch.data.datasource.UserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {
    @Binds
    fun bindsRegionDataSource(dataSource: RegionDataSourceImpl): RegionDataSource

    @Binds
    fun bindsUserDataSource(dataSource: UserDataSourceImpl): UserDataSource
}
