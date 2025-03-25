package com.oreocube.booksearch.data.repository

import com.oreocube.booksearch.data.response.BookAvailabilityDTO
import com.oreocube.booksearch.data.response.BookDetailDTO
import com.oreocube.booksearch.data.service.LibraryService
import com.oreocube.booksearch.domain.model.Book
import com.oreocube.booksearch.domain.model.BookAvailability
import com.oreocube.booksearch.domain.model.BookDetail
import com.oreocube.booksearch.domain.model.Library
import com.oreocube.booksearch.domain.model.param.BookAvailabilityCheckParam
import com.oreocube.booksearch.domain.model.param.BookDetailParam
import com.oreocube.booksearch.domain.model.param.BookSearchParam
import com.oreocube.booksearch.domain.model.param.LibrarySearchParam
import com.oreocube.booksearch.domain.repository.LibraryRepository
import javax.inject.Inject

class LibraryRepositoryImpl @Inject constructor(
    private val libraryService: LibraryService,
) : LibraryRepository {
    override suspend fun searchLibrariesByRegion(param: LibrarySearchParam): List<Library> {
        return libraryService.searchLibraries(districtId = param.districtId).response.libs.map {
            it.lib.toModel()
        }
    }

    override suspend fun searchBooks(param: BookSearchParam): List<Book> {
        return libraryService.searchBooks(title = param.title).response.docs.map {
            it.doc.toModel()
        }
    }

    override suspend fun checkBookAvailability(param: BookAvailabilityCheckParam): BookAvailability {
        return libraryService.checkBookAvailability(
            libraryCode = param.libraryCode,
            isbn = param.isbn,
        ).response.result.run(BookAvailabilityDTO::toModel)
    }

    override suspend fun getBookDetail(param: BookDetailParam): BookDetail {
        return libraryService.getBookDetail(
            isbn = param.isbn,
        ).response.detail.first().book.run(BookDetailDTO::toModel)
    }
}
