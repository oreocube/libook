package com.oreocube.booksearch.domain.usecase

import androidx.paging.PagingData
import com.oreocube.booksearch.domain.model.Book
import com.oreocube.booksearch.domain.model.param.BookSearchParam
import com.oreocube.booksearch.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchBooksUseCase @Inject constructor(
    private val libraryRepository: LibraryRepository,
) {
    suspend operator fun invoke(query: String): Flow<PagingData<Book>> {
        return libraryRepository.searchBooks(
            BookSearchParam(query)
        )
    }
}
