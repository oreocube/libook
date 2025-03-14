package com.oreocube.booksearch.feature.favorite.library

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object FavoriteLibraryRoute

fun NavGraphBuilder.favoriteLibraryScreen() {
    composable<FavoriteLibraryRoute> {
        FavoriteLibraryRoute()
    }
}
