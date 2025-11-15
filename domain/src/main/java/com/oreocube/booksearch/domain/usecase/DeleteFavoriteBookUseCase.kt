package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.repository.FavoriteBookRepository
import javax.inject.Inject

class DeleteFavoriteBookUseCase @Inject constructor(
    private val favoriteBookRepository: FavoriteBookRepository
) {
    suspend operator fun invoke(isbn: String) {
        favoriteBookRepository.deleteFavoriteBook(isbn)
    }
}
