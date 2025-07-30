package com.oreocube.booksearch.feature.book

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import kotlinx.serialization.Serializable

@Serializable
data class BookDetailRoute(
    val isbn: String,
)

fun NavController.navigateToBookDetail(isbn: String) {
    navigate(route = BookDetailRoute(isbn = isbn))
}

fun NavGraphBuilder.bookDetailScreen(
    onBackClick: () -> Unit,
    onAddLibraryClick: () -> Unit,
    onBookItemClick: (String) -> Unit,
    onShowSnackbar: (String) -> Unit,
) {
    composable<BookDetailRoute>(
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "libook://book-detail?isbn={isbn}"
            }
        )
    ) {
        BookDetailRoute(
            onBackClick = onBackClick,
            onAddLibraryClick = onAddLibraryClick,
            onBookItemClick = onBookItemClick,
            onShowSnackbar = onShowSnackbar,
        )
    }
}
