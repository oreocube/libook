package com.oreocube.booksearch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.oreocube.booksearch.core.navigation.TopLevelDestination
import com.oreocube.booksearch.feature.favorite.library.navigateToFavoriteLibrary
import com.oreocube.booksearch.feature.home.navigateToHome

@Stable
class BookSearchAppState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(destination: TopLevelDestination) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (destination) {
            TopLevelDestination.HOME -> navController.navigateToHome(navOptions)
            TopLevelDestination.FAVORITE_LIBRARY -> navController.navigateToFavoriteLibrary(
                navOptions
            )
        }
    }
}

@Composable
fun rememberBookSearchAppState(
    navController: NavHostController = rememberNavController(),
): BookSearchAppState {
    return remember(
        navController,
    ) {
        BookSearchAppState(
            navController = navController,
        )
    }
}
