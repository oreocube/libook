package com.oreocube.booksearch.feature.region.model

import com.oreocube.booksearch.domain.model.City

data class CityUiState(
    val id: Int,
    val name: String,
)

fun City.toUiState() = CityUiState(
    id = id,
    name = name,
)
