package com.oreocube.booksearch.feature.book.model

import com.oreocube.booksearch.core.ui.util.toFormattedDate
import com.oreocube.booksearch.domain.model.BookInfo
import com.oreocube.booksearch.domain.model.RecentBookHistory

data class RecentHistoryUiState(
    val isbn: String,
    val title: String,
    val authors: String,
    val imageUrl: String,
    val searchedAt: String,
)

fun RecentBookHistory.toUiState() = RecentHistoryUiState(
    isbn = isbn,
    title = title,
    authors = authors,
    imageUrl = imageUrl,
    searchedAt = searchedAt.toFormattedDate("MM.dd")
)

fun RecentHistoryUiState.toBook() = BookInfo(
    isbn = isbn,
    title = title,
    authors = authors,
    imageUrl = imageUrl
)
