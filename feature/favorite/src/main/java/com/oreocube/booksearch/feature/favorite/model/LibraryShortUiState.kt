package com.oreocube.booksearch.feature.favorite.model

import com.oreocube.booksearch.domain.model.LibraryShort

data class LibraryShortUiState(
    val id: String,
    val name: String,
)

fun LibraryShort.toUiState() = LibraryShortUiState(
    id = id,
    name = name,
)
