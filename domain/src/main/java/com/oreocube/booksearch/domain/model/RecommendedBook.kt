package com.oreocube.booksearch.domain.model

data class RecommendedBook(
    val title: String,
    val authors: String,
    val publisher: String,
    val publicationYear: String,
    val isbn13: String,
    val imageUrl: String,
)
