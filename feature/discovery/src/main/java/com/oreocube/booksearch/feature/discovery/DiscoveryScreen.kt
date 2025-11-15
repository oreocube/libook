package com.oreocube.booksearch.feature.discovery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.oreocube.booksearch.core.ui.theme.LiBookPreviewTheme
import com.oreocube.booksearch.core.ui.theme.Typography

@Composable
internal fun DiscoveryScreen(
    viewModel: DiscoveryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DiscoveryScreen(
        uiState = uiState,
    )
}

@Composable
private fun DiscoveryScreen(
    modifier: Modifier = Modifier,
    uiState: DiscoveryUiState,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 64.dp, horizontal = 24.dp)
    ) {
        Text(
            text = "즐겨찾기한 도서",
            style = Typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(uiState.favoriteBooks) {
                DiscoveryItem(
                    book = it
                )
            }
        }
    }
}

@Composable
private fun DiscoveryItem(
    book: DiscoveryBookUiModel,
) {
    Column(Modifier.width(100.dp)) {
        AsyncImage(
            modifier = Modifier.width(100.dp),
            model = book.imageUrl,
            contentDescription = null,
        )
        Text(
            text = book.title,
        )
        Text(
            text = book.authors,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun DiscoveryScreenPreview() {
    LiBookPreviewTheme {
        DiscoveryScreen(
            uiState = DiscoveryUiState(
                favoriteBooks = listOf(
                    DiscoveryBookUiModel(
                        isbn = "",
                        title = "title",
                        authors = "authors",
                        imageUrl = "",
                    ),
                    DiscoveryBookUiModel(
                        isbn = "",
                        title = "title",
                        authors = "authors",
                        imageUrl = "",
                    ),
                    DiscoveryBookUiModel(
                        isbn = "",
                        title = "title",
                        authors = "authors",
                        imageUrl = "",
                    ),
                )
            )
        )
    }
}
