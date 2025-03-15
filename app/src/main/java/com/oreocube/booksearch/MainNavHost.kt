package com.oreocube.booksearch

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.oreocube.booksearch.feature.book.SearchBookRoute
import com.oreocube.booksearch.feature.book.bookDetailScreen
import com.oreocube.booksearch.feature.book.navigateToBookDetail
import com.oreocube.booksearch.feature.book.searchBookScreen
import com.oreocube.booksearch.feature.favorite.library.favoriteLibraryScreen
import com.oreocube.booksearch.feature.library.navigateToSearchLibrary
import com.oreocube.booksearch.feature.library.searchLibraryScreen
import com.oreocube.booksearch.feature.region.regionScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    appState: BookSearchAppState,
) {
    val navController = appState.navController
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SearchBookRoute,
    ) {
        regionScreen(
            onSearchButtonClick = navController::navigateToSearchLibrary
        )
        searchLibraryScreen()
        searchBookScreen(
            onBookClick = navController::navigateToBookDetail
        )
        bookDetailScreen()
        favoriteLibraryScreen()
    }
}
