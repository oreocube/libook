package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.model.BookInfo
import com.oreocube.booksearch.domain.repository.FavoriteBookRepository
import javax.inject.Inject

class GetFavoriteBookUseCase @Inject constructor(
    private val favoriteBookRepository: FavoriteBookRepository
) {
    suspend operator fun invoke(): List<BookInfo> {
        return favoriteBookRepository.getFavoriteBooks()
    }
}
