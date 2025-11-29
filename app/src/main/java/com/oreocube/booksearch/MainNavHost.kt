package com.oreocube.booksearch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import com.oreocube.booksearch.feature.book.detail.bookDetailScreen
import com.oreocube.booksearch.feature.book.detail.navigateToBookDetail
import com.oreocube.booksearch.feature.book.search.barcode.BookBarcodeScannerRoute
import com.oreocube.booksearch.feature.book.search.barcode.bookBarcodeScannerScreen
import com.oreocube.booksearch.feature.book.search.barcode.navigateToBookBarcodeScanner
import com.oreocube.booksearch.feature.book.search.navigateToSearchBook
import com.oreocube.booksearch.feature.book.search.searchBookScreen
import com.oreocube.booksearch.feature.discovery.discoveryScreen
import com.oreocube.booksearch.feature.favorite.favoriteLibraryScreen
import com.oreocube.booksearch.feature.home.HomeRoute
import com.oreocube.booksearch.feature.home.homeScreen
import com.oreocube.booksearch.feature.library.navigateToSearchLibrary
import com.oreocube.booksearch.feature.library.searchLibraryScreen
import com.oreocube.booksearch.feature.region.navigateToRegion
import com.oreocube.booksearch.feature.region.regionScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    deepLinkString: String? = null,
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
            onSearchBarClick = navController::navigateToSearchBook,
            onBookItemClick = navController::navigateToBookDetail,
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
                navController.popBackStack()
                navController.popBackStack()
            },
        )
        searchBookScreen(
            onBackClick = navController::popBackStack,
            onBookClick = navController::navigateToBookDetail,
            onBarcodeScanClick = navController::navigateToBookBarcodeScanner,
            onShowSnackbar = onShowSnackbar,
        )
        bookDetailScreen(
            onBackClick = navController::popBackStack,
            onAddLibraryClick = navController::navigateToRegion,
            onBookItemClick = navController::navigateToBookDetail,
            onShowSnackbar = onShowSnackbar,
        )
        favoriteLibraryScreen(
            onSearchClick = navController::navigateToRegion
        )
        discoveryScreen(
            onBookItemClick = navController::navigateToBookDetail,
        )
        bookBarcodeScannerScreen(
            onBackClick = navController::popBackStack,
            onScanSuccess = { isbn ->
                navController.navigateToBookDetail(
                    isbn,
                    NavOptions.Builder()
                        .setPopUpTo<BookBarcodeScannerRoute>(inclusive = true)
                        .build()
                )
            },
        )
    }

    LaunchedEffect(deepLinkString) {
        deepLinkString?.let { uriString ->
            val uri = uriString.toUri()
            navController.navigate(deepLink = uri)
        }
    }
}
