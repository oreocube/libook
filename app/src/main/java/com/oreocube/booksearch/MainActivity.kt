package com.oreocube.booksearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.oreocube.booksearch.core.ui.theme.BooksearchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BooksearchTheme {
                MainScreen(
                    appState = rememberBookSearchAppState()
                )
            }
        }
    }
}
