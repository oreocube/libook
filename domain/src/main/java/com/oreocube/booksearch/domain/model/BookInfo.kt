package com.oreocube.booksearch.domain.model

data class BookInfo(
    val isbn: String,
    val title: String,
    val authors: String,
    val imageUrl: String,
)
