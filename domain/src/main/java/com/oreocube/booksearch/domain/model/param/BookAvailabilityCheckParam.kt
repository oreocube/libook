package com.oreocube.booksearch.domain.model.param

data class BookAvailabilityCheckParam(
    val isbn: String,
    val libraryCode: String,
)
