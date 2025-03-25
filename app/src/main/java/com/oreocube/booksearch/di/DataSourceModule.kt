package com.oreocube.booksearch.di

import com.oreocube.booksearch.data.datasource.RegionDataSource
import com.oreocube.booksearch.data.datasource.RegionDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {
    @Binds
    fun bindsRegionDataSource(dataSource: RegionDataSourceImpl): RegionDataSource
}
