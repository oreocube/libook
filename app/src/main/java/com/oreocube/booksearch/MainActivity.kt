package com.oreocube.booksearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.oreocube.booksearch.core.ui.theme.BooksearchTheme
import com.oreocube.booksearch.feature.library.navigateToSearchLibrary
import com.oreocube.booksearch.feature.library.searchLibraryScreen
import com.oreocube.booksearch.feature.region.RegionRoute
import com.oreocube.booksearch.feature.region.regionScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BooksearchTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = RegionRoute,
                ) {
                    regionScreen(
                        onSearchButtonClick = navController::navigateToSearchLibrary
                    )
                    searchLibraryScreen()
                }
            }
        }
    }
}
