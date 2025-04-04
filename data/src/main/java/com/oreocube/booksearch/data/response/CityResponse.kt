package com.oreocube.booksearch.data.response

import androidx.annotation.Keep
import com.oreocube.booksearch.domain.model.City

@Keep
data class CityResponse(
    val id: Int = 0,
    val name: String = "",
) {
    fun toModel() = City(
        id = id,
        name = name,
    )
}
