package com.oreocube.booksearch

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.oreocube.booksearch.core.ui.theme.BooksearchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val _deepLinkState = mutableStateOf<String?>(null)

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
        _deepLinkState.value = intent?.getStringExtra(DEEP_LINK_KEY)
        setContent {
            BooksearchTheme {
                val navController = rememberNavController()
                val appState = rememberBookSearchAppState(navController = navController)

                MainScreen(
                    appState = appState,
                    deepLinkString = _deepLinkState.value,
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        _deepLinkState.value = intent?.getStringExtra(DEEP_LINK_KEY)
    }
}
