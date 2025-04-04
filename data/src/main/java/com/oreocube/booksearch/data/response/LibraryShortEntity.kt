package com.oreocube.booksearch.data.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.oreocube.booksearch.domain.model.LibraryShort

@Entity(tableName = "library")
data class LibraryShortEntity(
    @PrimaryKey
    val id: String,
    val name: String,
) {
    fun toModel() = LibraryShort(
        id = id,
        name = name,
    )
}

fun LibraryShort.toEntity() = LibraryShortEntity(
    id = id,
    name = name,
)
