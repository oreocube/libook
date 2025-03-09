package com.oreocube.booksearch.domain.model

data class Book(
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
