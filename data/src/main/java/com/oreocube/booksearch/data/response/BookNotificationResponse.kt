package com.oreocube.booksearch.data.response

import androidx.annotation.Keep
import com.oreocube.booksearch.domain.model.param.BookNotificationTarget

@Keep
data class BookNotificationResponse(
    val libraryId: String = "",
    val libraryName: String = "",
    val isbn: String = "",
    val bookTitle: String = "",
) {
    fun toModel() = BookNotificationTarget(
        libraryId = libraryId,
        libraryName = libraryName,
        isbn = isbn,
        bookTitle = bookTitle,
    )
}
