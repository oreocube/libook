package com.oreocube.booksearch.domain.repository

import com.oreocube.booksearch.domain.model.BookInfo

interface FavoriteBookRepository {
    suspend fun getFavoriteBooks(): List<BookInfo>
    suspend fun addFavoriteBook(book: BookInfo)
    suspend fun deleteFavoriteBook(isbn: String)
}
