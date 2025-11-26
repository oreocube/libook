package com.oreocube.booksearch.domain.model

data class RecentBookHistory(
    val title: String,
    val isbn: String,
    val authors: String,
    val imageUrl: String,
    val searchedAt: Long,
)
