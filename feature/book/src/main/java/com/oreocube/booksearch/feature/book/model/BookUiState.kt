package com.oreocube.booksearch.feature.book.model

import com.oreocube.booksearch.domain.model.Book

data class BookUiState(
    val title: String,
    val authors: String,
    val publisher: String,
    val publicationYear: String,
    val isbn13: String,
    val vol: String,
    val imageUrl: String,
    val detailUrl: String,
    val loanCount: Long,
)

fun Book.toUiState() = BookUiState(
    title = title,
    authors = authors,
    publisher = publisher,
    publicationYear = publicationYear,
    isbn13 = isbn13,
    vol = vol,
    imageUrl = imageUrl,
    detailUrl = detailUrl,
    loanCount = loanCount
)
