package com.oreocube.booksearch.feature.book

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.oreocube.booksearch.core.ui.R
import com.oreocube.booksearch.core.ui.component.LiBookTopBar
import com.oreocube.booksearch.core.ui.theme.Brown20
import com.oreocube.booksearch.core.ui.theme.Gray10
import com.oreocube.booksearch.core.ui.theme.Gray20
import com.oreocube.booksearch.core.ui.theme.Gray90
import com.oreocube.booksearch.domain.model.BookDetail
import com.oreocube.booksearch.domain.model.LibraryShort
import com.oreocube.booksearch.feature.book.model.BookStatusUiState
import com.oreocube.booksearch.feature.book.model.RecommendedBookUiState

@Composable
fun BookDetailRoute(
    onBackClick: () -> Unit,
    onAddLibraryClick: () -> Unit,
    onBookItemClick: (String) -> Unit,
    onShowSnackbar: (String) -> Unit,
    viewModel: BookDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BookDetailScreen(
        modifier = Modifier.fillMaxSize(),
        uiState = uiState,
        onBackClick = onBackClick,
        onAddLibraryClick = onAddLibraryClick,
        onBookItemClick = onBookItemClick,
    )

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is BookDetailUiEvent.Error -> {
                    onShowSnackbar(event.message)
                }
            }
        }
    }
}

@Composable
fun BookDetailScreen(
    modifier: Modifier = Modifier,
    uiState: BookDetailUiState,
    onBackClick: () -> Unit,
    onAddLibraryClick: () -> Unit,
    onBookItemClick: (String) -> Unit,
) {
    Column(modifier = modifier) {
        LiBookTopBar(onNavigationIconClick = onBackClick)
        when {
            uiState.isLoading -> {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }

            uiState.book != null -> {
                val scrollState = rememberScrollState()

                Column(
                    modifier = modifier
                        .verticalScroll(scrollState)
                        .background(color = Color.White)
                ) {
                    BookDetailContent(
                        modifier = Modifier.fillMaxWidth(),
                        book = uiState.book,
                    )
                    HorizontalDivider()
                    LibraryStatusForBook(
                        modifier = Modifier.fillMaxWidth(),
                        status = uiState.status,
                        onAddLibraryClick = onAddLibraryClick,
                    )
                    if (uiState.recommendBooks.isNotEmpty()) {
                        HorizontalDivider()
                        RecommendedBookSection(
                            books = uiState.recommendBooks,
                            onBookItemClick = onBookItemClick,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BookDetailContent(
    modifier: Modifier = Modifier,
    book: BookDetail
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                model = book.imageUrl,
                contentDescription = "book image",
            )
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 8.dp),
            ) {
                Text(
                    text = book.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = book.authors,
                    fontSize = 14.sp,
                    color = Gray20
                )
                Text(
                    text = "${book.publisher} · ${book.publicationYear}",
                    fontSize = 14.sp,
                    color = Gray20,
                )
            }
        }
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "책 소개",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = book.description,
            fontSize = 16.sp,
            color = Gray10
        )
    }
}

@Composable
private fun LibraryStatusForBook(
    modifier: Modifier = Modifier,
    status: List<Pair<LibraryShort, BookStatusUiState>>,
    onAddLibraryClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        Text(
            text = "관심 도서관 비치 현황",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
        if (status.isEmpty()) {
            EmptyFavoriteLibraryContent(
                onAddLibraryClick = onAddLibraryClick
            )
        } else {
            status.forEach { (library, availability) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(color = Gray90)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = library.name, fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    StatusLabel(
                        modifier = Modifier.padding(start = 8.dp),
                        text = availability.text,
                        textColor = availability.textColor,
                        containerColor = availability.containerColor,
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyFavoriteLibraryContent(
    onAddLibraryClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.no_favorite_library_and_extra_information),
            style = MaterialTheme.typography.bodyMedium,
            color = Gray20,
            textAlign = TextAlign.Center,
        )
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .clip(shape = RoundedCornerShape(32.dp))
                .background(color = Brown20)
                .clickable(onClick = onAddLibraryClick)
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                tint = Color.White,
                painter = painterResource(R.drawable.ic_search_24),
                contentDescription = stringResource(R.string.menu_search_library)
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(R.string.menu_search_library),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
            )
        }
    }
}

@Composable
private fun StatusLabel(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    containerColor: Color,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(color = containerColor)
            .padding(vertical = 2.dp, horizontal = 8.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 12.sp,
        )
    }
}

@Composable
private fun RecommendedBookSection(
    books: List<RecommendedBookUiState>,
    onBookItemClick: (String) -> Unit,
) {
    Column {
        Text(
            modifier = Modifier.padding(top = 16.dp, start = 16.dp),
            text = "같이 볼만한 도서",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(
                items = books,
                key = { book -> book.isbn13 }
            ) { book ->
                RecommendedBookItem(book = book, onClick = onBookItemClick)
            }
        }
    }
}

@Composable
private fun RecommendedBookItem(
    book: RecommendedBookUiState,
    onClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .width(120.dp)
            .clickable { onClick(book.isbn13) }
    ) {
        AsyncImage(
            modifier = Modifier.size(width = 120.dp, height = 150.dp),
            model = book.imageUrl,
            contentDescription = "recommended book image",
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = book.title,
            fontSize = 14.sp,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun LibraryStatusForBookPreview1() {
    LibraryStatusForBook(
        status = listOf(
            LibraryShort("1", "도서관1") to BookStatusUiState.ON_LOAN,
            LibraryShort("1", "도서관2") to BookStatusUiState.NOT_AVAILABLE,
            LibraryShort("1", "도서관3") to BookStatusUiState.AVAILABLE,
        ),
        onAddLibraryClick = {},
    )
}

@Composable
@Preview(showBackground = true)
private fun LibraryStatusForBookPreview2() {
    LibraryStatusForBook(
        status = emptyList(),
        onAddLibraryClick = {},
    )
}

@Composable
@Preview(showBackground = true)
private fun BookDetailScreenPreview() {
    BookDetailScreen(
        uiState = BookDetailUiState(
            isLoading = false,
            book = BookDetail(
                title = "실용주의 프로그래머 :20주년 기념판 ",
                authors = "데이비드 토머스,정지용 옮김",
                publisher = "인사이트",
                publicationYear = "2022",
                isbn13 = "9788966263363",
                imageUrl = "https://image.aladin.co.kr/product/28878/64/cover/8966263364_1.jpg",
                description = "실용주의 프로그래머 20주년 기념판. 데이비드 토마스와 앤드류 헌트는 소프트웨어 산업에 큰 영향을 미친 이 책의 1판을 1999년에 썼다. 고객들이 더 나은 소프트웨어를 만들고 코딩의 기쁨을 재발견하도록 돕기 위해서였다."
            ),
            status = listOf(
                LibraryShort("1", "도서관1") to BookStatusUiState.ON_LOAN,
                LibraryShort("1", "도서관2") to BookStatusUiState.NOT_AVAILABLE,
                LibraryShort("1", "도서관3") to BookStatusUiState.AVAILABLE,
            ),
            recommendBooks = listOf(
                RecommendedBookUiState(
                    title = "실용주의 프로그래머 :20주년 기념판 ",
                    authors = "데이비드 토머스,정지용 옮김",
                    publisher = "인사이트",
                    publicationYear = "2022",
                    isbn13 = "978",
                    imageUrl = "https://image.aladin.co.kr/product/28878/64/cover/8966263364_1.jpg",
                ),
            ),
        ),
        onBackClick = {},
        onAddLibraryClick = {},
        onBookItemClick = {},
    )
}
