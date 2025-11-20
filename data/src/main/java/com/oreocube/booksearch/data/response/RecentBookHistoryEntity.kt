package com.oreocube.booksearch.data.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.oreocube.booksearch.domain.model.RecentBookHistory

@Entity(tableName = "recent_book_history")
data class RecentBookHistoryEntity(
    @PrimaryKey val isbn: String,
    val title: String,
    @ColumnInfo(defaultValue = "")
    val authors: String,
    @ColumnInfo(defaultValue = "")
    val imageUrl: String,
    val searchedAt: Long,
) {
    fun toModel() = RecentBookHistory(
        isbn = isbn,
        title = title,
        authors = authors,
        imageUrl = imageUrl,
        searchedAt = searchedAt,
    )
}

fun RecentBookHistory.toEntity() = RecentBookHistoryEntity(
    isbn = isbn,
    title = title,
    authors = authors,
    imageUrl = imageUrl,
    searchedAt = searchedAt,
)
