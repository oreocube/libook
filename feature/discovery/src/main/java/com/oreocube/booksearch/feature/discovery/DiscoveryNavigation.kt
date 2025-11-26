package com.oreocube.booksearch.feature.discovery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object DiscoveryRoute

fun NavController.navigateToDiscovery(
    navOptions: NavOptions? = null,
) {
    navigate(route = DiscoveryRoute, navOptions)
}

fun NavGraphBuilder.discoveryScreen(
    onBookItemClick: (String) -> Unit,
) {
    composable<DiscoveryRoute> {
        DiscoveryRoute(
            onBookItemClick = onBookItemClick,
        )
    }
}

@Composable
internal fun DiscoveryRoute(
    onBookItemClick: (String) -> Unit,
    viewModel: DiscoveryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DiscoveryScreen(
        uiState = uiState,
        onBookItemClick = onBookItemClick,
    )

    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }
}
