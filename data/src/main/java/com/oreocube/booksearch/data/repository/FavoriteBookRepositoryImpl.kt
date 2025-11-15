package com.oreocube.booksearch.data.repository

import com.oreocube.booksearch.data.database.FavoriteBookDao
import com.oreocube.booksearch.data.response.toEntity
import com.oreocube.booksearch.data.response.toModel
import com.oreocube.booksearch.domain.model.BookInfo
import com.oreocube.booksearch.domain.repository.FavoriteBookRepository
import javax.inject.Inject

class FavoriteBookRepositoryImpl @Inject constructor(
    private val favoriteBookDao: FavoriteBookDao
): FavoriteBookRepository {
    override suspend fun getFavoriteBooks(): List<BookInfo> {
        return favoriteBookDao.getFavoriteBooks().map { it.toModel() }
    }

    override suspend fun addFavoriteBook(book: BookInfo) {
        favoriteBookDao.addFavoriteBook(book.toEntity())
    }

    override suspend fun deleteFavoriteBook(isbn: String) {
        favoriteBookDao.deleteFavoriteBook(isbn)
    }
}
