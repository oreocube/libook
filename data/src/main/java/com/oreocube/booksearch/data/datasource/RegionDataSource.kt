package com.oreocube.booksearch.data.datasource

import com.oreocube.booksearch.data.response.CityResponse
import com.oreocube.booksearch.data.response.DistrictResponse

interface RegionDataSource {
    suspend fun getCities(): List<CityResponse>
    suspend fun getDistricts(cityId: Int): List<DistrictResponse>
}
