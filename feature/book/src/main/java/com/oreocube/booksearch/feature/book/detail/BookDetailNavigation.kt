package com.oreocube.booksearch.feature.book.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import kotlinx.serialization.Serializable

@Serializable
data class BookDetailRoute(
    val isbn: String,
)

fun NavController.navigateToBookDetail(isbn: String, options: NavOptions? = null) {
    navigate(route = BookDetailRoute(isbn = isbn), navOptions = options)
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
