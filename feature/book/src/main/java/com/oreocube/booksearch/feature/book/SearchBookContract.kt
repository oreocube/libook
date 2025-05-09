package com.oreocube.booksearch.feature.book

import com.oreocube.booksearch.feature.book.model.BookUiState
import com.oreocube.booksearch.feature.book.model.RecentHistoryUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SearchBookUiState(
    val query: String = "",
    val result: ImmutableList<BookUiState> = persistentListOf(),
    val recentHistory: ImmutableList<RecentHistoryUiState> = persistentListOf(),
)

sealed class SearchBookUiEvent {
    data class Error(val message: String) : SearchBookUiEvent()
    data class NavigateToBookDetail(val isbn: String) : SearchBookUiEvent()
}

sealed class SearchBookUiAction {
    data class InputChanged(val input: String = "") : SearchBookUiAction()
    data class BookClicked(val book: BookUiState) : SearchBookUiAction()
    data class HistoryItemClick(val history: RecentHistoryUiState) : SearchBookUiAction()
    data class DeleteHistoryClick(val history: RecentHistoryUiState) : SearchBookUiAction()
    data object ClearHistoryClick : SearchBookUiAction()
}
