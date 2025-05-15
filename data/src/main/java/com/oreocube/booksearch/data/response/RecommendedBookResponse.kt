package com.oreocube.booksearch.data.response

import com.oreocube.booksearch.domain.model.RecommendedBook
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendedBookResponse(
    @SerialName("resultNum")
    val count: Long,
    @SerialName("docs")
    val docs: List<RecommendedBookWrapper>,
)

@Serializable
data class RecommendedBookWrapper(
    @SerialName("book")
    val book: RecommendedBookDTO,
)

@Serializable
data class RecommendedBookDTO(
    @SerialName("bookname")
    val title: String, // 도서명
    @SerialName("authors")
    val authors: String, // 저자명
    @SerialName("publisher")
    val publisher: String, // 출판사
    @SerialName("publication_year")
    val publicationYear: String? = null, // 출판년도
    @SerialName("isbn13")
    val isbn13: String, // 13 자리 ISBN
    @SerialName("bookImageURL")
    val imageUrl: String, // 책표지 URL
) {
    fun toModel() = RecommendedBook(
        title = title,
        authors = authors,
        publisher = publisher,
        publicationYear = publicationYear.orEmpty(),
        isbn13 = isbn13,
        imageUrl = imageUrl,
    )
}
