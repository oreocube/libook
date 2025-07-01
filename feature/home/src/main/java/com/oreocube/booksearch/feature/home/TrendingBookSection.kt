package com.oreocube.booksearch.feature.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oreocube.booksearch.core.ui.theme.Gray80
import com.oreocube.booksearch.domain.model.TrendingBook
import kotlinx.coroutines.delay

@Composable
internal fun TrendingBooksSection(
    modifier: Modifier = Modifier,
    books: List<TrendingBook>,
) {
    if (books.isEmpty()) return

    var currentIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000L)
            currentIndex = (currentIndex + 1) % books.size
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = Gray80, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedContent(
            targetState = books[currentIndex],
            transitionSpec = {
                slideInVertically(
                    initialOffsetY = { it } // 아래에서 등장
                ) + fadeIn() togetherWith
                        slideOutVertically(
                            targetOffsetY = { -it } // 위로 사라짐
                        ) + fadeOut()
            },
            label = "SlideUpBookTransition"
        ) { book ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "${book.no} ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    fontSize = 14.sp,
                    text = book.title,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    fontSize = 14.sp,
                    text = "▲ ${book.difference}"
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun TrendingBooksSectionPreview() {
    TrendingBooksSection(
        books = listOf(
            TrendingBook(
                1, difference = 12,
                title = "실용주의 프로그래머 :20주년 기념판 ",
                authors = "데이비드 토머스,정지용 옮김",
                publisher = "인사이트",
                publicationYear = "2022",
                isbn13 = "9788966263363",
            )
        )
    )
}
