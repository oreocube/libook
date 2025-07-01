package com.oreocube.booksearch.domain.repository

import androidx.paging.PagingData
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
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface LibraryRepository {
    suspend fun searchLibrariesByRegion(param: LibrarySearchParam): List<Library>
    suspend fun searchBooks(param: BookSearchParam): Flow<PagingData<Book>>
    suspend fun checkBookAvailability(param: BookAvailabilityCheckParam): BookAvailability
    suspend fun getBookDetail(param: BookDetailParam): BookDetail
    suspend fun getRecommendedBooks(isbn: String): List<RecommendedBook>
    suspend fun getTrendingBooks(searchDate: LocalDate): List<TrendingBook>
}
