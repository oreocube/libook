package com.oreocube.booksearch.feature.book.model

import com.oreocube.booksearch.core.ui.util.toFormattedDate
import com.oreocube.booksearch.domain.model.RecentBookHistory

data class RecentHistoryUiState(
    val isbn: String,
    val title: String,
    val searchedAt: String,
)

fun RecentBookHistory.toUiState() = RecentHistoryUiState(
    isbn = isbn,
    title = title,
    searchedAt = searchedAt.toFormattedDate("MM.dd")
)
