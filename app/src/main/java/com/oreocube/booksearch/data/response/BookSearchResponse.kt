package com.oreocube.booksearch.data.response

import com.oreocube.booksearch.domain.model.Book
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookSearchResponse(
    @SerialName("numFound")
    val count: Long,
    @SerialName("docs")
    val docs: List<BookWrapper>,
)

@Serializable
data class BookWrapper(
    @SerialName("doc")
    val doc: BookDTO,
)

@Serializable
data class BookDTO(
    @SerialName("bookname")
    val title: String, // 도서명
    @SerialName("authors")
    val authors: String, // 저자명
    @SerialName("publisher")
    val publisher: String, // 출판사
    @SerialName("publication_year")
    val publicationYear: String, // 출판년도
    @SerialName("isbn13")
    val isbn13: String, // 13 자리 ISBN
    @SerialName("vol")
    val vol: String,// 권
    @SerialName("bookImageURL")
    val imageUrl: String, // 책표지 URL
    @SerialName("bookDtlUrl")
    val detailUrl: String, // 도서 상세 페이지 URL
    @SerialName("loan_count")
    val loanCount: Long, // 대출건수
) {
    fun toModel() = Book(
        title = title,
        authors = authors,
        publisher = publisher,
        publicationYear = publicationYear,
        isbn13 = isbn13,
        vol = vol,
        imageUrl = imageUrl,
        detailUrl = detailUrl,
        loanCount = loanCount,
    )
}
