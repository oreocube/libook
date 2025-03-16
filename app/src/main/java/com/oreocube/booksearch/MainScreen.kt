package com.oreocube.booksearch

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.oreocube.booksearch.core.navigation.TopLevelDestination
import com.oreocube.booksearch.core.ui.component.BookSearchNavigationBar
import com.oreocube.booksearch.core.ui.component.BookSearchNavigationItem

@Composable
fun MainScreen(
    appState: BookSearchAppState,
) {
    Scaffold(
        bottomBar = {
            val currentDestination = appState.currentDestination
            val isTopLevelDestination = currentDestination.isTopLevelDestination()

            BookSearchNavigationBar(
                isVisible = isTopLevelDestination,
            ) {
                appState.topLevelDestinations.forEach { destination ->
                    val selected = currentDestination?.hierarchy?.any {
                        it.hasRoute(destination.route)
                    } == true

                    BookSearchNavigationItem(
                        destination = destination,
                        selected = selected,
                        onClick = { appState.navigateToTopLevelDestination(destination) },
                    )
                }
            }
        },
    ) { innerPadding ->
        MainNavHost(
            modifier = Modifier.padding(innerPadding),
            appState = appState,
        )
    }
}

private fun NavDestination?.isTopLevelDestination(): Boolean {
    return TopLevelDestination.entries.any { this?.hasRoute(it.route) == true }
}
