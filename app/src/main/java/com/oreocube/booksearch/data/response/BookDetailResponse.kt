package com.oreocube.booksearch.data.response

import com.oreocube.booksearch.domain.model.BookDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDetailResponse(
    val detail: List<BookDetailWrapper>
)

@Serializable
data class BookDetailWrapper(
    val book: BookDetailDTO
)

@Serializable
data class BookDetailDTO(
    @SerialName("bookname")
    val title: String, // 도서명
    @SerialName("authors")
    val authors: String, // 저자명
    @SerialName("publisher")
    val publisher: String, // 출판사
    @SerialName("publication_year")
    val publicationYear: String? = null, // 출판년도
    @SerialName("isbn13")
    val isbn13: String,
    @SerialName("description")
    val description: String,
    @SerialName("bookImageURL")
    val imageUrl: String, // 책표지 URL)
) {
    fun toModel() = BookDetail(
        title = title,
        authors = authors,
        publisher = publisher,
        publicationYear = publicationYear.orEmpty(),
        isbn13 = isbn13,
        imageUrl = imageUrl,
        description = description,
    )
}
