package com.oreocube.booksearch.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.oreocube.booksearch.data.service.LibraryService
import com.oreocube.booksearch.domain.model.Book
import javax.inject.Inject

class BookPagingSource @Inject constructor(
    private val query: String,
    private val service: LibraryService,
) : PagingSource<Int, Book>() {

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.minus(1) ?: anchorPage?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        val currentPage = params.key ?: 1
        return try {
            val books = service.searchBooks(
                title = query,
                page = currentPage,
                pageSize = params.loadSize,
            ).response.docs.map {
                it.doc.toModel()
            }

            LoadResult.Page(
                data = books,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (books.size < params.loadSize) null else currentPage + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

object BookPagingConfig {
    const val PAGE_SIZE = 100
}
