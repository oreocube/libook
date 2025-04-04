package com.oreocube.booksearch.domain.repository

import com.oreocube.booksearch.domain.model.City
import com.oreocube.booksearch.domain.model.District
import com.oreocube.booksearch.domain.model.param.DistrictSearchParam

interface RegionRepository {
    suspend fun getCities(): List<City>
    suspend fun getDistricts(param: DistrictSearchParam): List<District>
}
