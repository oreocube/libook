package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.model.District
import com.oreocube.booksearch.domain.model.param.DistrictSearchParam
import com.oreocube.booksearch.domain.repository.RegionRepository
import javax.inject.Inject

class GetDistrictsUseCase @Inject constructor(
    private val regionRepository: RegionRepository,
) {
    suspend operator fun invoke(param: DistrictSearchParam): List<District> {
        return regionRepository.getDistricts(param).sortedBy(District::name)
    }
}
