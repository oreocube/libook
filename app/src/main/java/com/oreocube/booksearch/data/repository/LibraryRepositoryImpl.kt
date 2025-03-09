package com.oreocube.booksearch.data.repository

import com.oreocube.booksearch.domain.model.Book
import com.oreocube.booksearch.domain.model.BookAvailability
import com.oreocube.booksearch.domain.model.Library
import com.oreocube.booksearch.domain.model.param.BookAvailabilityCheckParam
import com.oreocube.booksearch.domain.model.param.BookSearchParam
import com.oreocube.booksearch.domain.model.param.LibrarySearchParam
import com.oreocube.booksearch.domain.repository.LibraryRepository

class LibraryRepositoryImpl : LibraryRepository {
    override suspend fun searchLibrariesByRegion(param: LibrarySearchParam): List<Library> {
        TODO("Not yet implemented")
    }

    override suspend fun searchBooks(param: BookSearchParam): List<Book> {
        TODO("Not yet implemented")
    }

    override suspend fun checkBookAvailability(param: BookAvailabilityCheckParam): BookAvailability {
        TODO("Not yet implemented")
    }
}
