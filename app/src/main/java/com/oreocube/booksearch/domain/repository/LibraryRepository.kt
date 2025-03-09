package com.oreocube.booksearch.domain.repository

import com.oreocube.booksearch.domain.model.Book
import com.oreocube.booksearch.domain.model.BookAvailability
import com.oreocube.booksearch.domain.model.Library
import com.oreocube.booksearch.domain.model.param.BookAvailabilityCheckParam
import com.oreocube.booksearch.domain.model.param.BookSearchParam
import com.oreocube.booksearch.domain.model.param.LibrarySearchParam

interface LibraryRepository {
    suspend fun searchLibrariesByRegion(param: LibrarySearchParam): List<Library>
    suspend fun searchBooks(param: BookSearchParam): List<Book>
    suspend fun checkBookAvailability(param: BookAvailabilityCheckParam): BookAvailability
}
