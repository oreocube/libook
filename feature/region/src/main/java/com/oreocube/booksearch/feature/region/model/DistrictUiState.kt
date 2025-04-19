package com.oreocube.booksearch.feature.region.model

import com.oreocube.booksearch.domain.model.District

data class DistrictUiState(
    val id: Int,
    val cityId: Int,
    val name: String,
)

fun District.toUiState() = DistrictUiState(
    id = id,
    cityId = cityId,
    name = name,
)
