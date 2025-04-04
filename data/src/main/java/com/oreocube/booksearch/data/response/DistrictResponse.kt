package com.oreocube.booksearch.data.response

import androidx.annotation.Keep
import com.oreocube.booksearch.domain.model.District

@Keep
data class DistrictResponse(
    val id: Int = 0,
    val city_id: Int = 0,
    val name: String = "",
) {
    fun toModel() = District(
        id = id,
        cityId = city_id,
        name = name,
    )
}
