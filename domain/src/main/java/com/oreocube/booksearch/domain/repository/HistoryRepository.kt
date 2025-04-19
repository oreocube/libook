package com.oreocube.booksearch.domain.repository

import com.oreocube.booksearch.domain.model.RecentBookHistory

interface HistoryRepository {
    suspend fun getAll(): List<RecentBookHistory>
    suspend fun add(item: RecentBookHistory)
    suspend fun deleteByISBN(isbn: String)
    suspend fun deleteAll()
}
