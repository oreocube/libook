package com.oreocube.booksearch.feature.book

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SearchBookRoute

fun NavController.navigateToSearchBook(
    navOptions: NavOptions? = null,
) {
    navigate(route = SearchBookRoute, navOptions)
}

fun NavGraphBuilder.searchBookScreen() {
    composable<SearchBookRoute> {
        SearchBookRoute()
    }
}

