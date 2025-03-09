package com.oreocube.booksearch.data.repository

import com.oreocube.booksearch.data.datasource.RegionDataSource
import com.oreocube.booksearch.data.response.CityResponse
import com.oreocube.booksearch.data.response.DistrictResponse
import com.oreocube.booksearch.domain.model.City
import com.oreocube.booksearch.domain.model.District
import com.oreocube.booksearch.domain.model.param.DistrictSearchParam
import com.oreocube.booksearch.domain.repository.RegionRepository
import javax.inject.Inject

class RegionRepositoryImpl @Inject constructor(
    private val dataSource: RegionDataSource,
) : RegionRepository {
    override suspend fun getCities(): List<City> {
        return dataSource.getCities().map(CityResponse::toModel)
    }

    override suspend fun getDistricts(param: DistrictSearchParam): List<District> {
        return dataSource.getDistricts(param.cityId).map(DistrictResponse::toModel)
    }
}
