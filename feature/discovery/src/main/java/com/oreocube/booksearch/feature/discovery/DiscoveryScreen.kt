package com.oreocube.booksearch.feature.discovery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.oreocube.booksearch.core.ui.component.LiBookTopBar
import com.oreocube.booksearch.core.ui.theme.LiBookPreviewTheme

@Composable
internal fun DiscoveryScreen(
    modifier: Modifier = Modifier,
    uiState: DiscoveryUiState,
    onBookItemClick: (String) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LiBookTopBar(
            title = "탐색",
            description = "활동 기록으로 읽을 책을 찾을 수 있어요",
        )
        DiscoveryBookSection(
            title = "관심 도서",
            books = uiState.favoriteBooks,
            onItemClick = onBookItemClick,
        )
        DiscoveryBookSection(
            title = "최근 검색한 도서",
            books = uiState.recentBooks,
            onItemClick = onBookItemClick,
        )
    }
}

@Composable
private fun DiscoveryBookSection(
    modifier: Modifier = Modifier,
    title: String,
    books: List<DiscoveryBookUiModel>,
    onItemClick: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF374151)
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(books) {
                DiscoveryItem(
                    book = it,
                    onItemClick = { onItemClick(it.isbn) },
                )
            }
        }
    }
}

@Composable
private fun DiscoveryItem(
    book: DiscoveryBookUiModel,
    onItemClick: () -> Unit,
) {
    Column(
        Modifier
            .width(100.dp)
            .clickable(onClick = onItemClick)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(width = 120.dp, height = 150.dp)
                .clip(RoundedCornerShape(16.dp)),
            model = book.imageUrl,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = book.title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = Color(0xFF1F2937),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = book.authors,
            fontSize = 12.sp,
            color = Color(0xFF6B7280)
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
                ),
                recentBooks = listOf(
                    DiscoveryBookUiModel(
                        isbn = "",
                        title = "title",
                        authors = "authors",
                        imageUrl = "",
                    ),
                )
            ),
            onBookItemClick = {},
        )
    }
}
