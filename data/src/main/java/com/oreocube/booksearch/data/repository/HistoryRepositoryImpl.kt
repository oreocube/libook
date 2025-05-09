package com.oreocube.booksearch.data.repository

import com.oreocube.booksearch.data.database.RecentBookHistoryDao
import com.oreocube.booksearch.data.response.RecentBookHistoryEntity
import com.oreocube.booksearch.data.response.toEntity
import com.oreocube.booksearch.domain.model.RecentBookHistory
import com.oreocube.booksearch.domain.repository.HistoryRepository
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyDao: RecentBookHistoryDao,
) : HistoryRepository {
    override suspend fun getAll(): List<RecentBookHistory> {
        return historyDao.getAll().map(RecentBookHistoryEntity::toModel)
    }

    override suspend fun add(item: RecentBookHistory) {
        historyDao.insert(history = item.toEntity())
    }

    override suspend fun deleteByISBN(isbn: String) {
        historyDao.deleteByISBN(isbn = isbn)
    }

    override suspend fun deleteAll() {
        historyDao.clear()
    }
}
