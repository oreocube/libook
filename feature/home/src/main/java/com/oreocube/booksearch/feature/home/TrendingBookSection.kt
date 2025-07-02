package com.oreocube.booksearch.feature.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.oreocube.booksearch.core.ui.component.icon.LiBookIcons
import com.oreocube.booksearch.core.ui.component.icon.TrendingUp
import com.oreocube.booksearch.core.ui.theme.Gray80
import com.oreocube.booksearch.domain.model.TrendingBook

@Composable
internal fun TrendingBooksSection(
    modifier: Modifier = Modifier,
    books: List<TrendingBook>?,
    onBookItemClick: (String) -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFF3F4F6))
    ) {
        Column {
            TrendingBooksHeader()

            if (books == null) {
                repeat(2) {
                    TrendingBookSkeleton()
                }
            } else {
                books.forEach { book ->
                    key(book.no) {
                        TrendingBookItem(
                            modifier = Modifier.fillMaxWidth(),
                            book = book,
                            onBookItemClick = onBookItemClick,
                        )
                    }
                    if (book != books.last()) {
                        HorizontalDivider(
                            color = Color(0xFFF3F4F6)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TrendingBooksHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFFEF2F2),
                        Color(0xFFFFF7ED)
                    )
                )
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "🔥",
                fontSize = 18.sp
            )
            Text(
                text = "대출 급상승 도서",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF374151)
            )
            Icon(
                imageVector = LiBookIcons.Default.TrendingUp,
                contentDescription = "상승",
                tint = Color(0xFFEF4444),
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

val SkeletonLightGray = Gray80

@Composable
private fun TrendingBookSkeleton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = SkeletonLightGray,
                    shape = CircleShape,
                ),
        )

        Box(
            modifier = Modifier
                .size(width = 48.dp, height = 64.dp)
                .background(
                    color = SkeletonLightGray,
                    shape = RoundedCornerShape(8.dp),
                ),
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(16.dp)
                    .background(
                        color = SkeletonLightGray,
                        shape = RoundedCornerShape(4.dp),
                    ),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(16.dp)
                    .background(
                        color = SkeletonLightGray,
                        shape = RoundedCornerShape(4.dp),
                    ),
            )
        }
    }
}

@Composable
private fun TrendingBookItem(
    modifier: Modifier = Modifier,
    book: TrendingBook,
    onBookItemClick: (String) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onBookItemClick(book.isbn13) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TrendingBookRank(book)

        // Book Cover
        AsyncImage(
            modifier = Modifier
                .size(width = 48.dp, height = 64.dp)
                .clip(RoundedCornerShape(8.dp)),
            placeholder = BrushPainter(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFEF3C7),
                        Color(0xFFFED7AA)
                    )
                ),
            ),
            model = book.imageUrl,
            contentScale = ContentScale.Crop,
            contentDescription = "cover",
        )

        TrendingBookInfo(
            modifier = Modifier.weight(1f),
            book = book,
        )

        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = "더보기",
            tint = Color(0xFFD1D5DB),
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun TrendingBookInfo(
    modifier: Modifier = Modifier,
    book: TrendingBook,
) {
    Column(modifier = modifier) {
        Text(
            text = book.title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = Color(0xFF1F2937),
            maxLines = 2,
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

private fun getRankColor(rank: Int): Color = when (rank) {
    1 -> Color(0xFFFEF3C7)
    2 -> Color(0xFFF3F4F6)
    3 -> Color(0xFFFED7AA)
    else -> Color(0xFFDDEAFE)
}

private fun getRankTextColor(rank: Int): Color = when (rank) {
    1 -> Color(0xFFB45309)
    2 -> Color(0xFF6B7280)
    3 -> Color(0xFFEA580C)
    else -> Color(0xFF2563EB)
}

@Composable
private fun TrendingBookRank(book: TrendingBook) {
    Column(
        modifier = Modifier.heightIn(min = 64.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = getRankColor(book.no),
                    shape = CircleShape,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = book.no.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = getRankTextColor(book.no),
            )
        }

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFFEF4444),
                        fontSize = 12.sp,
                    )
                ) {
                    append("▲")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF6B7280),
                        fontSize = 12.sp,
                    )
                ) {
                    append(book.difference.toString())
                }
            },
            fontSize = 12.sp,
            color = Color(0xFF6B7280),
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun TrendingBookSkeletonPreview() {
    TrendingBooksSection(
        books = null,
        onBookItemClick = {},
    )
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
                imageUrl = "",
            )
        ),
        onBookItemClick = {},
    )
}
