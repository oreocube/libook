package com.oreocube.booksearch.feature.book.detail

import com.oreocube.booksearch.domain.model.BookDetail
import com.oreocube.booksearch.feature.book.model.LibraryBookStatusUiState
import com.oreocube.booksearch.feature.book.model.RecommendedBookUiState

data class BookDetailUiState(
    val isLoading: Boolean,
    val book: BookDetail?,
    val status: List<LibraryBookStatusUiState>,
    val recommendBooks: List<RecommendedBookUiState> = emptyList(),
) {
    companion object {
        val initialState = BookDetailUiState(
            isLoading = true,
            book = null,
            status = emptyList(),
            recommendBooks = emptyList(),
        )
    }
}

sealed class BookDetailUiEvent {
    data class Error(val message: String) : BookDetailUiEvent()
}
