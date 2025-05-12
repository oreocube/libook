package com.oreocube.booksearch.feature.book.model

import com.oreocube.booksearch.domain.model.RecommendedBook

data class RecommendedBookUiState(
    val title: String,
    val authors: String,
    val publisher: String,
    val publicationYear: String,
    val isbn13: String,
    val imageUrl: String,
)

fun RecommendedBook.toUiState() = RecommendedBookUiState(
    title = title,
    authors = authors,
    publisher = publisher,
    publicationYear = publicationYear,
    isbn13 = isbn13,
    imageUrl = imageUrl,
)
