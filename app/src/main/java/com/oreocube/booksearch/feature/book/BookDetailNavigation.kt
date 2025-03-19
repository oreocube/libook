package com.oreocube.booksearch.feature.book

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
    onShowSnackbar: (String) -> Unit,
) {
    composable<BookDetailRoute> {
        BookDetailRoute(
            onBackClick = onBackClick,
            onAddLibraryClick = onAddLibraryClick,
            onShowSnackbar = onShowSnackbar,
        )
    }
}
