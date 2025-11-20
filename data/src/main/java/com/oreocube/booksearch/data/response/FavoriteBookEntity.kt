package com.oreocube.booksearch.data.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.oreocube.booksearch.domain.model.BookInfo

@Entity(tableName = "favorite_books")
data class FavoriteBookEntity(
    @PrimaryKey
    val isbn: String,
    val title: String,
    val author: String,
    val imageUrl: String,
    val createdAt: Long = System.currentTimeMillis()
)

fun FavoriteBookEntity.toModel() = BookInfo(
    isbn = isbn,
    title = title,
    authors = author,
    imageUrl = imageUrl,
)

fun BookInfo.toFavoriteBookEntity() = FavoriteBookEntity(
    isbn = isbn,
    title = title,
    author = authors,
    imageUrl = imageUrl,
)
