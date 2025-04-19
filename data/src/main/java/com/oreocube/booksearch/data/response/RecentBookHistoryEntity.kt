package com.oreocube.booksearch.data.response

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_book_history")
data class RecentBookHistoryEntity(
    @PrimaryKey val isbn: String,
    val title: String,
    val searchedAt: Long,
)
