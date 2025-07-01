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
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
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
import com.oreocube.booksearch.domain.model.TrendingBook

@Composable
internal fun TrendingBooksSection(
    modifier: Modifier = Modifier,
    books: List<TrendingBook>,
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
            // Header
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
                        text = "인기 급상승 도서",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF374151)
                    )
                    Icon(
                        imageVector = TrendingUpIcon,
                        contentDescription = "상승",
                        tint = Color(0xFFEF4444),
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

            books.forEach { book ->
                key(book.no) {
                    TrendingBookItem(
                        modifier = Modifier.fillMaxWidth(),
                        book = book
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

@Composable
private fun TrendingBookItem(
    modifier: Modifier = Modifier,
    book: TrendingBook,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Rank
        Column(
            modifier = Modifier.heightIn(min = 64.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        color = when (book.no) {
                            1 -> Color(0xFFFEF3C7)
                            2 -> Color(0xFFF3F4F6)
                            3 -> Color(0xFFFED7AA)
                            else -> Color(0xFFDDEAFE)
                        },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = book.no.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = when (book.no) {
                        1 -> Color(0xFFB45309)
                        2 -> Color(0xFF6B7280)
                        3 -> Color(0xFFEA580C)
                        else -> Color(0xFF2563EB)
                    }
                )
            }

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFFEF4444),
                            fontSize = 12.sp
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
                        append("${book.difference}")
                    }
                },
                fontSize = 12.sp,
                color = Color(0xFF6B7280),
                fontWeight = FontWeight.Medium
            )
        }

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

        // Book Info
        Column(
            modifier = Modifier.weight(1f)
        ) {
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
            Spacer(modifier = Modifier.height(4.dp))

        }

        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = "더보기",
            tint = Color(0xFFD1D5DB),
            modifier = Modifier.size(20.dp)
        )
    }
}

private val TrendingUpIcon = Builder(
    name = "TrendingUp",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f,
).apply {
    path(
        fill = SolidColor(Color.Black),
        fillAlpha = 1.0f,
        stroke = null,
        strokeAlpha = 1.0f,
        strokeLineWidth = 1.0f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Miter,
        strokeLineMiter = 1.0f,
        pathFillType = PathFillType.NonZero
    ) {
        moveTo(16f, 6f)
        lineToRelative(2.29f, 2.29f)
        lineToRelative(-4.88f, 4.88f)
        lineToRelative(-4f, -4f)
        lineTo(2f, 16.59f)
        lineTo(3.41f, 18f)
        lineToRelative(6f, -6f)
        lineToRelative(4f, 4f)
        lineToRelative(6.3f, -6.29f)
        lineTo(22f, 12f)
        verticalLineTo(6f)
        horizontalLineTo(16f)
        close()
    }
}.build()

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
        )
    )
}
