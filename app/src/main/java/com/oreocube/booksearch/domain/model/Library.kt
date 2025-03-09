package com.oreocube.booksearch.domain.model

data class Library(
    val id: String,
    val name: String,
    val address: String,
    val tel: String,
    val fax: String,
    val latitude: String,
    val longitude: String,
    val homepageUrl: String,
    val closedTime: String,
    val operatingTime: String,
    val bookCount: Long,
)
