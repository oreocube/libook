package com.oreocube.booksearch

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.oreocube.booksearch.core.ui.theme.BooksearchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
        )
        super.onCreate(savedInstanceState)
        viewModel.anonymousLogin()
        setContent {
            BooksearchTheme {
                val navController = rememberNavController()
                val appState = rememberBookSearchAppState(navController = navController)
                val deepLinkString = intent?.extras?.getString("deepLink")

                MainScreen(
                    appState = appState,
                    deepLinkString = deepLinkString,
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
}
