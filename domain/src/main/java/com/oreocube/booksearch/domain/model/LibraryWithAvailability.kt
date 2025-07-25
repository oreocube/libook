package com.oreocube.booksearch.domain.model

data class LibraryWithAvailability(
    val library: LibraryShort,
    val availability: Result<BookAvailability>,
    val isNotificationRegistered: Boolean = false,
)
