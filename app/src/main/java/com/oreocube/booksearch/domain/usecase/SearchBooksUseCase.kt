package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.model.Book
import com.oreocube.booksearch.domain.model.param.BookSearchParam
import com.oreocube.booksearch.domain.repository.LibraryRepository
import javax.inject.Inject

class SearchBooksUseCase @Inject constructor(
    private val libraryRepository: LibraryRepository,
) {
    suspend operator fun invoke(query: String): List<Book> {
        return libraryRepository.searchBooks(
            BookSearchParam(query)
        )
    }
}
