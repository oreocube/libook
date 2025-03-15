package com.oreocube.booksearch

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.oreocube.booksearch.core.ui.component.BookSearchNavigationItem

@Composable
fun MainScreen(
    appState: BookSearchAppState,
) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentDestination = appState.currentDestination

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
