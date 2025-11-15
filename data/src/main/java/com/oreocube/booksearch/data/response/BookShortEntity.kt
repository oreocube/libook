package com.oreocube.booksearch.data.response

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_books")
data class BookShortEntity(
    @PrimaryKey
    val isbn: String,
    val title: String,
    val author: String,
    val imageUrl: String,
)
