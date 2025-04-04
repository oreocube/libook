package com.oreocube.booksearch.feature.favorite

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object FavoriteLibraryRoute

fun NavController.navigateToFavoriteLibrary(
    navOptions: NavOptions? = null,
) {
    navigate(route = FavoriteLibraryRoute, navOptions)
}

fun NavGraphBuilder.favoriteLibraryScreen(
    onSearchClick: () -> Unit,
) {
    composable<FavoriteLibraryRoute> {
        FavoriteLibraryRoute(
            onSearchClick = onSearchClick,
        )
    }
}
