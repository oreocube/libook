package com.oreocube.booksearch.feature.book.model

import androidx.compose.ui.graphics.Color
import com.oreocube.booksearch.core.ui.theme.Gray20
import com.oreocube.booksearch.core.ui.theme.Gray80
import com.oreocube.booksearch.core.ui.theme.Green30
import com.oreocube.booksearch.core.ui.theme.Green80
import com.oreocube.booksearch.core.ui.theme.Red30
import com.oreocube.booksearch.core.ui.theme.Red80
import com.oreocube.booksearch.domain.model.BookAvailability
import com.oreocube.booksearch.domain.model.LibraryShort
import com.oreocube.booksearch.domain.model.LibraryWithAvailability

enum class BookStatusUiState(
    val text: String,
    val textColor: Color,
    val containerColor: Color,
) {
    NOT_AVAILABLE(
        text = "미보유",
        textColor = Gray20,
        containerColor = Gray80,
    ),
    ON_LOAN(
        text = "대출중",
        textColor = Red30,
        containerColor = Red80,
    ),
    AVAILABLE(
        text = "대출가능",
        textColor = Green30,
        containerColor = Green80,
    ),
    ERROR(
        text = "오류",
        textColor = Gray20,
        containerColor = Gray80,
    )
}

fun BookAvailability.toUiState(): BookStatusUiState {
    return when {
        !hasBook -> BookStatusUiState.NOT_AVAILABLE
        !loanAvailable -> BookStatusUiState.ON_LOAN
        else -> BookStatusUiState.AVAILABLE
    }
}

fun LibraryWithAvailability.toUiState(): Pair<LibraryShort, BookStatusUiState> {
    return when {
        availability.isSuccess -> library to availability.getOrThrow().toUiState()
        else -> library to BookStatusUiState.ERROR
    }
}
