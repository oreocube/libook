package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.model.City
import com.oreocube.booksearch.domain.repository.RegionRepository
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(
    private val regionRepository: RegionRepository,
) {
    suspend operator fun invoke(): List<City> {
        return regionRepository.getCities()
    }
}
