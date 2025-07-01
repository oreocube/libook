package com.oreocube.booksearch.domain.model

data class TrendingBook(
    val no: Int,
    val difference: Int,
    val title: String,
    val authors: String,
    val publisher: String,
    val publicationYear: String,
    val isbn13: String,
)
