package com.oreocube.booksearch.feature.book

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import com.oreocube.booksearch.core.ui.R
import com.oreocube.booksearch.core.ui.component.BookSearchTextField
import com.oreocube.booksearch.core.ui.theme.Brown30
import com.oreocube.booksearch.core.ui.theme.Gray10
import com.oreocube.booksearch.core.ui.theme.Gray20
import com.oreocube.booksearch.core.ui.theme.Gray40
import com.oreocube.booksearch.feature.book.model.BookUiState
import com.oreocube.booksearch.feature.book.model.RecentHistoryUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchBookRoute(
    onBackClick: () -> Unit,
    onBookClick: (String) -> Unit,
    onShowSnackbar: (String) -> Unit,
    viewModel: SearchBookViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SearchBookScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
        onBackClick = onBackClick,
    )

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is SearchBookUiEvent.Error -> {
                    onShowSnackbar(event.message)
                }

                is SearchBookUiEvent.NavigateToBookDetail -> {
                    onBookClick(event.isbn)
                }
            }
        }
    }
}

@Composable
fun SearchBookScreen(
    uiState: SearchBookUiState,
    onAction: (SearchBookUiAction) -> Unit,
    onBackClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val pagingData = uiState.result.collectAsLazyPagingItems()

    Column(modifier = Modifier.fillMaxSize()) {
        BookSearchTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            input = uiState.query,
            placeholder = stringResource(R.string.search_book_hint),
            leadingIcon = {
                IconButton(
                    modifier = Modifier.padding(start = 8.dp),
                    onClick = onBackClick
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_back_24),
                        contentDescription = null,
                        tint = Brown30,
                    )
                }
            },
            onInputChanged = { onAction(SearchBookUiAction.InputChanged(it)) },
            onClearClicked = { onAction(SearchBookUiAction.InputChanged()) },
            onQuerySubmitted = {
                keyboardController?.hide()
                focusManager.clearFocus()
            },
        )
        if (uiState.query.isBlank() && uiState.recentHistory.isNotEmpty()) {
            RecentHistoryContainer(
                modifier = Modifier.weight(1f),
                histories = uiState.recentHistory,
                onAction = onAction,
            )
        } else {
            SearchResult(
                modifier = Modifier.weight(1f),
                result = pagingData,
                onAction = onAction,
            )
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
private fun RecentHistoryContainer(
    modifier: Modifier = Modifier,
    histories: ImmutableList<RecentHistoryUiState>,
    onAction: (SearchBookUiAction) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        item {
            Row(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "최근 검색 기록",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Gray10,
                )
                Text(
                    modifier = Modifier.clickable { onAction(SearchBookUiAction.ClearHistoryClick) },
                    text = "전체 삭제",
                    fontSize = 14.sp,
                    color = Gray20,
                )
            }
        }
        items(
            items = histories,
            key = { history -> history.isbn }
        ) { history ->
            Row(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .height(48.dp)
                    .clickable { onAction(SearchBookUiAction.HistoryItemClick(history)) },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    text = history.title,
                    fontSize = 14.sp,
                    color = Gray10,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = history.searchedAt,
                    fontSize = 14.sp,
                    color = Gray20,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                IconButton(
                    modifier = Modifier.padding(end = 4.dp),
                    onClick = { onAction(SearchBookUiAction.DeleteHistoryClick(history)) }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_clear_24),
                        contentDescription = null,
                        tint = Gray20
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchResult(
    modifier: Modifier = Modifier,
    result: LazyPagingItems<BookUiState>,
    onAction: (SearchBookUiAction) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(
            count = result.itemCount,
            key = result.itemKey { it.isbn13 },
        ) { index ->
            val book = result[index] ?: return@items
            BookItem(
                book = book,
                onItemClick = { onAction(SearchBookUiAction.BookClicked(book)) }
            )
            if (index < result.itemCount) {
                HorizontalDivider(color = Gray40)
            }
        }
    }
}

@Composable
private fun BookItem(
    modifier: Modifier = Modifier,
    book: BookUiState,
    onItemClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = true, onClick = onItemClick)
            .padding(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier.size(80.dp),
            model = book.imageUrl,
            contentDescription = "book image",
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = book.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Gray10,
            )
            Text(
                text = book.authors,
                fontSize = 14.sp,
                color = Gray20,
            )
            Text(
                text = "${book.publisher} · ${book.publicationYear}",
                fontSize = 14.sp,
                color = Gray20,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun BookItemPreview() {
    BookItem(
        book = BookUiState(
            title = "실용주의 프로그래머 :20주년 기념판 ",
            authors = "데이비드 토머스,정지용 옮김",
            publisher = "인사이트",
            publicationYear = "2022",
            isbn13 = "9788966263363",
            vol = "",
            imageUrl = "https://image.aladin.co.kr/product/28878/64/cover/8966263364_1.jpg",
            detailUrl = "https://data4library.kr/bookV?seq=6404790",
            loanCount = 695
        ),
        onItemClick = {}
    )
}

@Composable
@Preview(showBackground = true)
private fun RecentHistoryPreview() {
    RecentHistoryContainer(
        modifier = Modifier.fillMaxWidth(),
        histories = listOf(
            RecentHistoryUiState(
                isbn = "1",
                title = "가나다라마바사아자차카타파하",
                searchedAt = "04.19",
            ),
            RecentHistoryUiState(
                isbn = "2",
                title = "가나다라마바사아자차카타파하",
                searchedAt = "04.19",
            ),
        ).toImmutableList(),
        onAction = {},
    )
}

@Composable
@Preview(showBackground = true)
private fun SearchBookScreenPreview1() {
    SearchBookScreen(
        uiState = SearchBookUiState(),
        onBackClick = {},
        onAction = {},
    )
}

@Composable
@Preview(showBackground = true)
private fun SearchBookScreenPreview2() {
    val fakeData = listOf(
        BookUiState(
            title = "실용주의 프로그래머 :20주년 기념판 ",
            authors = "데이비드 토머스,정지용 옮김",
            publisher = "인사이트",
            publicationYear = "2022",
            isbn13 = "9788966263363",
            vol = "",
            imageUrl = "https://image.aladin.co.kr/product/28878/64/cover/8966263364_1.jpg",
            detailUrl = "https://data4library.kr/bookV?seq=6404790",
            loanCount = 695
        )
    )

    SearchBookScreen(
        uiState = SearchBookUiState(
            query = "실용주의",
            result = flowOf(PagingData.from(fakeData)),
        ),
        onBackClick = {},
        onAction = {},
    )
}
