package com.oreocube.booksearch.data.response

import com.oreocube.booksearch.domain.model.TrendingBook
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendingBookResponse(
    val results: List<TrendingBookResultWrapperDto>
)

@Serializable
data class TrendingBookResultWrapperDto(
    val result: TrendingBookResultDto
)

@Serializable
data class TrendingBookResultDto(
    val date: String,
    val docs: List<TrendingBookWrapperDto>
)

@Serializable
data class TrendingBookWrapperDto(
    val doc: TrendingBookDTO
)

@Serializable
data class TrendingBookDTO(
    @SerialName("no")
    val no: Int,
    @SerialName("difference")
    val difference: Int,
    @SerialName("baseWeekRank")
    val baseWeekRank: Int,
    @SerialName("pastWeekRank")
    val pastWeekRank: Int,
    @SerialName("bookname")
    val title: String,
    @SerialName("authors")
    val author: String,
    @SerialName("publisher")
    val publisher: String, // 출판사
    @SerialName("publication_year")
    val publicationYear: String? = null, // 출판년도
    @SerialName("isbn13")
    val isbn13: String, // 13 자리 ISBN
    @SerialName("vol")
    val vol: String,// 권
    @SerialName("bookImageURL")
    val imageUrl: String, // 책표지 URL
    @SerialName("bookDtlUrl")
    val detailUrl: String, // 도서 상세 페이지 URL
) {
    fun toModel() = TrendingBook(
        no = no,
        difference = difference,
        title = title,
        authors = author,
        publisher = publisher,
        publicationYear = publicationYear.orEmpty(),
        isbn13 = isbn13,
        imageUrl = imageUrl,
    )
}
