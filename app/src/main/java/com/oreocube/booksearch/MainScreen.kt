package com.oreocube.booksearch

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy

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

                    NavigationBarItem(
                        selected = selected,
                        onClick = { appState.navigateToTopLevelDestination(destination) },
                        icon = {
                            if (selected) {
                                Icon(
                                    painter = painterResource(destination.selectedIcon),
                                    contentDescription = stringResource(destination.label),
                                )
                            } else {
                                Icon(
                                    painter = painterResource(destination.icon),
                                    contentDescription = stringResource(destination.label),
                                )
                            }
                        },
                        label = { Text(text = stringResource(destination.label)) },
                        alwaysShowLabel = true,
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
