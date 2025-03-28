package com.oreocube.booksearch.data.service

import com.oreocube.booksearch.BuildConfig
import com.oreocube.booksearch.data.response.BookAvailabilityResponse
import com.oreocube.booksearch.data.response.BookDetailResponse
import com.oreocube.booksearch.data.response.BookSearchResponse
import com.oreocube.booksearch.data.response.LibraryApiResponse
import com.oreocube.booksearch.data.response.LibrarySearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LibraryService {
    @GET("libSrch")
    suspend fun searchLibraries(
//        @Query("libCode") libraryCode: String,
        @Query("dtl_region") districtId: Int,
        @Query("pageNo") page: Int = 1,
        @Query("pageSize") pageSize: Int = 100,
        @Query("authKey") authKey: String = BuildConfig.LIBRARY_API_AUTH_KEY,
        @Query("format") format: String = JSON_FORMAT,
    ): LibraryApiResponse<LibrarySearchResponse>

    @GET("srchBooks")
    suspend fun searchBooks(
        @Query("title") title: String,
        @Query("pageNo") page: Int = 1,
        @Query("pageSize") pageSize: Int = 300,
        @Query("authKey") authKey: String = BuildConfig.LIBRARY_API_AUTH_KEY,
        @Query("format") format: String = JSON_FORMAT,
    ): LibraryApiResponse<BookSearchResponse>

    @GET("bookExist")
    suspend fun checkBookAvailability(
        @Query("libCode") libraryCode: String,
        @Query("isbn13") isbn: String,
        @Query("authKey") authKey: String = BuildConfig.LIBRARY_API_AUTH_KEY,
        @Query("format") format: String = JSON_FORMAT,
    ): LibraryApiResponse<BookAvailabilityResponse>

    @GET("srchDtlList")
    suspend fun getBookDetail(
        @Query("isbn13") isbn: String,
        @Query("authKey") authKey: String = BuildConfig.LIBRARY_API_AUTH_KEY,
        @Query("format") format: String = JSON_FORMAT,
    ): LibraryApiResponse<BookDetailResponse>

    companion object {
        private const val JSON_FORMAT = "json"
    }
}
