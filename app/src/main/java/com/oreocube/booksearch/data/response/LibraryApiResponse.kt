package com.oreocube.booksearch.data.response

import kotlinx.serialization.Serializable

@Serializable
data class LibraryApiResponse<T>(
    val response: T,
)
