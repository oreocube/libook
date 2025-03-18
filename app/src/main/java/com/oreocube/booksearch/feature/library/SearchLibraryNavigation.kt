package com.oreocube.booksearch.feature.library

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class SearchLibraryRoute(
    val districtId: Int,
)

fun NavController.navigateToSearchLibrary(districtId: Int) {
    navigate(route = SearchLibraryRoute(districtId = districtId))
}

fun NavGraphBuilder.searchLibraryScreen(
    onBackClick: () -> Unit,
    onCompleteClick: () -> Unit,
    onShowSnackbar: (String) -> Unit,
) {
    composable<SearchLibraryRoute> {
        SearchLibraryRoute(
            onBackClick = onBackClick,
            onCompleteClick = onCompleteClick,
            onShowSnackbar = onShowSnackbar,
        )
    }
}
