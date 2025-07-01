package com.oreocube.booksearch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.oreocube.booksearch.data.datasource.BookPagingConfig
import com.oreocube.booksearch.data.datasource.BookPagingSource
import com.oreocube.booksearch.data.response.BookAvailabilityDTO
import com.oreocube.booksearch.data.response.BookDetailDTO
import com.oreocube.booksearch.data.service.LibraryService
import com.oreocube.booksearch.domain.model.Book
import com.oreocube.booksearch.domain.model.BookAvailability
import com.oreocube.booksearch.domain.model.BookDetail
import com.oreocube.booksearch.domain.model.Library
import com.oreocube.booksearch.domain.model.RecommendedBook
import com.oreocube.booksearch.domain.model.TrendingBook
import com.oreocube.booksearch.domain.model.param.BookAvailabilityCheckParam
import com.oreocube.booksearch.domain.model.param.BookDetailParam
import com.oreocube.booksearch.domain.model.param.BookSearchParam
import com.oreocube.booksearch.domain.model.param.LibrarySearchParam
import com.oreocube.booksearch.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class LibraryRepositoryImpl @Inject constructor(
    private val libraryService: LibraryService,
) : LibraryRepository {
    override suspend fun searchLibrariesByRegion(param: LibrarySearchParam): List<Library> {
        return libraryService.searchLibraries(districtId = param.districtId).response.libs.map {
            it.lib.toModel()
        }
    }

    override suspend fun searchBooks(param: BookSearchParam): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = BookPagingConfig.PAGE_SIZE),
            pagingSourceFactory = {
                BookPagingSource(
                    query = param.title,
                    service = libraryService,
                )
            }
        ).flow
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

    override suspend fun getRecommendedBooks(isbn: String): List<RecommendedBook> {
        return libraryService.getRecommendedBooks(isbn = isbn).response.docs.map { it.book.toModel() }
    }

    override suspend fun getTrendingBooks(searchDate: LocalDate): List<TrendingBook> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val response = libraryService.getTrendingBooks(
            searchDate = searchDate.format(formatter),
        ).response
        val latest = response.results
            .maxByOrNull { LocalDate.parse(it.result.date, formatter) } ?: return emptyList()
        return latest.result.docs.map { it.doc.toModel() }
    }
}
