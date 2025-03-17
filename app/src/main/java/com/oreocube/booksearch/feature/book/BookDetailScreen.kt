package com.oreocube.booksearch.feature.book

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.oreocube.booksearch.R
import com.oreocube.booksearch.core.ui.component.BookSearchTopBar
import com.oreocube.booksearch.core.ui.theme.Brown20
import com.oreocube.booksearch.core.ui.theme.Gray10
import com.oreocube.booksearch.core.ui.theme.Gray20
import com.oreocube.booksearch.core.ui.theme.Gray30
import com.oreocube.booksearch.core.ui.theme.Green30
import com.oreocube.booksearch.core.ui.theme.Red30
import com.oreocube.booksearch.domain.model.BookAvailability
import com.oreocube.booksearch.domain.model.BookDetail
import com.oreocube.booksearch.domain.model.LibraryShort

@Composable
fun BookDetailRoute(
    onBackClick: () -> Unit,
    viewModel: BookDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BookDetailScreen(
        modifier = Modifier.fillMaxSize(),
        uiState = uiState,
        onBackClick = onBackClick,
    )
}

@Composable
fun BookDetailScreen(
    modifier: Modifier = Modifier,
    uiState: BookDetailUiState,
    onBackClick: () -> Unit,
) {
    Column(modifier = modifier) {
        BookSearchTopBar(
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_back_24),
                        contentDescription = stringResource(R.string.menu_back),
                    )
                }
            }
        )
        when (uiState) {
            is BookDetailUiState.Loading -> {}
            is BookDetailUiState.Data -> {
                val scrollState = rememberScrollState()

                Column(modifier = modifier.verticalScroll(scrollState)) {
                    BookDetailContent(
                        modifier = Modifier.fillMaxWidth(),
                        book = uiState.book,
                    )
                    HorizontalDivider()
                    LibraryStatusForBook(
                        modifier = Modifier.fillMaxWidth(),
                        status = uiState.status,
                    )
                }
            }
        }
    }
}

@Composable
fun BookDetailContent(
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
            text = book.description,
            fontSize = 16.sp,
            color = Gray10
        )
    }
}

@Composable
private fun LibraryStatusForBook(
    modifier: Modifier = Modifier,
    status: List<Pair<LibraryShort, BookAvailability>>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        Text(
            text = "소장 도서관",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
        status.forEach { (library, availability) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = library.name, fontSize = 16.sp)
                StatusLabel(
                    modifier = Modifier.padding(start = 8.dp),
                    text = if (availability.hasBook) "보유" else "미보유",
                    containerColor = if (availability.hasBook) Brown20 else Gray30
                )

                if (availability.hasBook) {
                    StatusLabel(
                        modifier = Modifier.padding(start = 8.dp),
                        text = if (availability.loanAvailable) "대출가능" else "대출중",
                        containerColor = if (availability.loanAvailable) Green30 else Red30
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusLabel(
    modifier: Modifier = Modifier,
    text: String,
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
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun LibraryStatusForBookPreview() {
    LibraryStatusForBook(
        status = listOf(
            LibraryShort("1", "도서관1") to BookAvailability(hasBook = true, loanAvailable = false),
            LibraryShort("1", "도서관2") to BookAvailability(hasBook = false, loanAvailable = false),
            LibraryShort("1", "도서관3") to BookAvailability(hasBook = true, loanAvailable = true),
        )
    )
}

@Composable
@Preview(showBackground = true)
private fun BookDetailScreenPreview() {
    BookDetailScreen(
        uiState = BookDetailUiState.Data(
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
                LibraryShort("1", "도서관1") to BookAvailability(
                    hasBook = true,
                    loanAvailable = false
                ),
                LibraryShort("1", "도서관2") to BookAvailability(
                    hasBook = false,
                    loanAvailable = false
                ),
                LibraryShort("1", "도서관3") to BookAvailability(
                    hasBook = true,
                    loanAvailable = true
                ),
            )
        ),
        onBackClick = {},
    )
}
