package com.oreocube.booksearch

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
//        topBar = {
//            TODO()
//        },
//        bottomBar = {
//            TODO()
//        },
    ) { innerPadding ->
        MainNavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController
        )
    }
}
