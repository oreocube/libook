package com.oreocube.booksearch.domain.model.param

data class BookNotificationTarget(
    val libraryId: String,
    val libraryName: String,
    val isbn: String,
    val bookTitle: String,
)
