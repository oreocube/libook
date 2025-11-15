package com.oreocube.booksearch.feature.discovery

data class DiscoveryUiState(
    val favoriteBooks: List<DiscoveryBookUiModel>,
) {
    companion object {
        val initialState = DiscoveryUiState(
            favoriteBooks = emptyList(),
        )
    }
}
