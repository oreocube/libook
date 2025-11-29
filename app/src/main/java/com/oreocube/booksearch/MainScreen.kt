package com.oreocube.booksearch

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.oreocube.booksearch.navigation.BookSearchNavigationBar
import com.oreocube.booksearch.navigation.BookSearchNavigationItem
import com.oreocube.booksearch.navigation.TopLevelDestination
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    appState: BookSearchAppState,
    deepLinkString: String? = null
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color.White,
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.padding(bottom = 80.dp),
                hostState = snackbarHostState,
            )
        },
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
            deepLinkString = deepLinkString,
            onShowSnackbar = { message ->
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(message)
                }
            }
        )
    }
}

private fun NavDestination?.isTopLevelDestination(): Boolean {
    return TopLevelDestination.entries.any { this?.hasRoute(it.route) == true }
}
