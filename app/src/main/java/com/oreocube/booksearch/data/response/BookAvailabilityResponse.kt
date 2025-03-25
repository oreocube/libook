package com.oreocube.booksearch.data.response

import com.oreocube.booksearch.domain.model.BookAvailability
import kotlinx.serialization.Serializable

@Serializable
data class BookAvailabilityResponse(
    val result: BookAvailabilityDTO,
)

@Serializable
data class BookAvailabilityDTO(
    val hasBook: String,
    val loanAvailable: String,
) {
    fun toModel() = BookAvailability(
        hasBook = hasBook == "Y",
        loanAvailable = loanAvailable == "Y",
    )
}
