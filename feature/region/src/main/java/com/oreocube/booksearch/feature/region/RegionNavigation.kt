package com.oreocube.booksearch.feature.region

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object RegionRoute

fun NavController.navigateToRegion(
    navOptions: NavOptions? = null,
) {
    navigate(route = RegionRoute, navOptions)
}

fun NavGraphBuilder.regionScreen(
    onBackClick: () -> Unit,
    onSearchButtonClick: (Int) -> Unit,
    onShowSnackbar: (String) -> Unit,
) {
    composable<RegionRoute> {
        RegionRoute(
            onBackClick = onBackClick,
            onSearchButtonClick = onSearchButtonClick,
            onShowSnackbar = onShowSnackbar,
        )
    }
}
