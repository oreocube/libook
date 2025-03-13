package com.oreocube.booksearch.feature.book

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.oreocube.booksearch.R
import com.oreocube.booksearch.core.ui.theme.Gray10
import com.oreocube.booksearch.core.ui.theme.Gray20
import com.oreocube.booksearch.domain.model.Book

@Composable
fun SearchBookRoute(
    onBookClick: (String) -> Unit,
    viewModel: SearchBookViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SearchBookScreen(
        uiState = uiState,
        onInputChanged = viewModel::onInputChanged,
        onClearClicked = viewModel::onInputChanged,
        onQuerySubmitted = {},
        onBookClick = onBookClick,
    )
}

@Composable
fun SearchBookScreen(
    uiState: SearchBookUiState,
    onInputChanged: (String) -> Unit,
    onClearClicked: () -> Unit,
    onQuerySubmitted: (String) -> Unit,
    onBookClick: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        BookSearchTextField(
            modifier = Modifier.fillMaxWidth(),
            input = uiState.query,
            onInputChanged = onInputChanged,
            onClearClicked = onClearClicked,
            onQuerySubmitted = onQuerySubmitted,
        )
        LazyColumn(modifier = Modifier.weight(1f)) {
            uiState.result.forEachIndexed { index, book ->
                item(key = book.isbn13) {
                    BookItem(
                        book = book,
                        onItemClick = { onBookClick(book.isbn13) }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
private fun BookSearchTextField(
    modifier: Modifier = Modifier,
    input: String,
    onInputChanged: (String) -> Unit,
    onClearClicked: () -> Unit,
    onQuerySubmitted: (String) -> Unit,
) {
    TextField(
        modifier = modifier,
        value = input,
        onValueChange = onInputChanged,
        label = { Text("검색어를 입력하세요") },
        trailingIcon = {
            if (input.isNotBlank())
                IconButton(onClick = onClearClicked) {
                    Icon(
                        painter = painterResource(R.drawable.ic_clear_24),
                        contentDescription = null,
                    )
                }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onQuerySubmitted(input) }
        ),
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
        )
    )
}

@Composable
private fun BookItem(
    modifier: Modifier = Modifier,
    book: Book,
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
        book = Book(
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
private fun SearchBookScreenPreview1() {
    SearchBookScreen(
        uiState = SearchBookUiState(),
        onInputChanged = {},
        onClearClicked = {},
        onQuerySubmitted = {},
        onBookClick = {},
    )
}

@Composable
@Preview(showBackground = true)
private fun SearchBookScreenPreview2() {
    SearchBookScreen(
        uiState = SearchBookUiState(
            query = "실용주의",
            result = listOf(
                Book(
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
            )
        ),
        onInputChanged = {},
        onClearClicked = {},
        onQuerySubmitted = {},
        onBookClick = {},
    )
}
