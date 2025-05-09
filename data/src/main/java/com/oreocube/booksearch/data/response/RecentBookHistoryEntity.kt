package com.oreocube.booksearch.data.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.oreocube.booksearch.domain.model.RecentBookHistory

@Entity(tableName = "recent_book_history")
data class RecentBookHistoryEntity(
    @PrimaryKey val isbn: String,
    val title: String,
    val searchedAt: Long,
) {
    fun toModel() = RecentBookHistory(
        isbn = isbn,
        title = title,
        searchedAt = searchedAt,
    )
}

fun RecentBookHistory.toEntity() = RecentBookHistoryEntity(
    isbn = isbn,
    title = title,
    searchedAt = searchedAt,
)
