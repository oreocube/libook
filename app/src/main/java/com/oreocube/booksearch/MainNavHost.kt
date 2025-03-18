package com.oreocube.booksearch

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.oreocube.booksearch.feature.book.bookDetailScreen
import com.oreocube.booksearch.feature.book.navigateToBookDetail
import com.oreocube.booksearch.feature.book.navigateToSearchBook
import com.oreocube.booksearch.feature.book.searchBookScreen
import com.oreocube.booksearch.feature.favorite.library.FavoriteLibraryRoute
import com.oreocube.booksearch.feature.favorite.library.favoriteLibraryScreen
import com.oreocube.booksearch.feature.home.HomeRoute
import com.oreocube.booksearch.feature.home.homeScreen
import com.oreocube.booksearch.feature.library.navigateToSearchLibrary
import com.oreocube.booksearch.feature.library.searchLibraryScreen
import com.oreocube.booksearch.feature.region.navigateToRegion
import com.oreocube.booksearch.feature.region.regionScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    appState: BookSearchAppState,
    onShowSnackbar: (String) -> Unit,
) {
    val navController = appState.navController
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeRoute,
    ) {
        homeScreen(
            onSearchBarClick = navController::navigateToSearchBook
        )
        regionScreen(
            onBackClick = navController::popBackStack,
            onSearchButtonClick = navController::navigateToSearchLibrary,
            onShowSnackbar = onShowSnackbar,
        )
        searchLibraryScreen(
            onBackClick = navController::popBackStack,
            onShowSnackbar = onShowSnackbar,
            onCompleteClick = {
                navController.popBackStack(FavoriteLibraryRoute, inclusive = false)
            },
        )
        searchBookScreen(
            onBookClick = navController::navigateToBookDetail
        )
        bookDetailScreen(
            onBackClick = navController::popBackStack,
        )
        favoriteLibraryScreen(
            onSearchClick = navController::navigateToRegion
        )
    }
}
