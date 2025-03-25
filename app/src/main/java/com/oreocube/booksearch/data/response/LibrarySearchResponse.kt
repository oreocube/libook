package com.oreocube.booksearch.data.response

import com.oreocube.booksearch.domain.model.Library
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LibrarySearchResponse(
    @SerialName("pageNo")
    val page: Int,
    @SerialName("pageSize")
    val pageSize: Int,
    @SerialName("numFound")
    val totalCount: Int,
    @SerialName("resultNum")
    val resultCount: Int,
    @SerialName("libs")
    val libs: List<LibraryWrapper>,
)

@Serializable
data class LibraryWrapper(
    @SerialName("lib")
    val lib: LibraryDTO,
)

@Serializable
data class LibraryDTO(
    @SerialName("libCode")
    val id: String,
    @SerialName("libName")
    val name: String,
    @SerialName("address")
    val address: String,
    @SerialName("tel")
    val tel: String,
    @SerialName("fax")
    val fax: String? = null,
    @SerialName("latitude")
    val latitude: String,
    @SerialName("longitude")
    val longitude: String,
    @SerialName("homepage")
    val homepageUrl: String,
    @SerialName("closed")
    val closedTime: String,
    @SerialName("operatingTime")
    val operatingTime: String,
    @SerialName("BookCount")
    val bookCount: String?,
) {
    fun toModel() = Library(
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
        bookCount = bookCount?.toLongOrNull() ?: 0,
    )
}
