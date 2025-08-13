package com.oreocube.booksearch.feature.book.detail

import com.oreocube.booksearch.domain.model.BookDetail
import com.oreocube.booksearch.domain.model.LibraryShort
import com.oreocube.booksearch.feature.book.model.LibraryBookStatusUiState
import com.oreocube.booksearch.feature.book.model.RecommendedBookUiState

data class BookDetailUiState(
    val isFirstEntry: Boolean = true,
    val isLoading: Boolean,
    val book: BookDetail?,
    val status: List<LibraryBookStatusUiState>,
    val recommendBooks: List<RecommendedBookUiState> = emptyList(),
) {
    companion object {
        val initialState = BookDetailUiState(
            isFirstEntry = true,
            isLoading = true,
            book = null,
            status = emptyList(),
            recommendBooks = emptyList(),
        )
    }
}

sealed class BookDetailSideEffect {
    data object NavigateToAddLibrary : BookDetailSideEffect()
    data class NavigateToBookDetail(val isbn: String) : BookDetailSideEffect()
    data class Error(val message: String) : BookDetailSideEffect()
}

sealed class BookDetailIntent {
    data object EnterScreen : BookDetailIntent()
    data object AddLibraryClick : BookDetailIntent()
    data class RefreshBookAvailability(
        val library: LibraryShort,
    ) : BookDetailIntent()

    data class ToggleNotification(
        val isRegistered: Boolean,
        val library: LibraryShort,
    ) : BookDetailIntent()

    data class BookItemClick(
        val isbn: String,
    ) : BookDetailIntent()
}
