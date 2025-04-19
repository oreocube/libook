package com.oreocube.booksearch.feature.library.model

import com.oreocube.booksearch.domain.model.Library
import com.oreocube.booksearch.domain.model.LibraryShort

data class LibraryUiState(
    val id: String,
    val name: String,
    val address: String,
    val tel: String,
    val fax: String?,
    val latitude: String,
    val longitude: String,
    val homepageUrl: String,
    val closedTime: String,
    val operatingTime: String,
    val bookCount: Long,
    val isFavorite: Boolean = false,
) {
    fun toShort() = LibraryShort(
        id = id,
        name = name,
    )
}

fun Library.toUiState() = LibraryUiState(
    id = id,
    name = name,
    address = address,
    tel = tel,
    fax = fax,
    latitude = latitude,
    longitude = longitude,
    homepageUrl = homepageUrl,
    closedTime = closedTime,
    operatingTime = operatingTime,
    bookCount = bookCount
)
