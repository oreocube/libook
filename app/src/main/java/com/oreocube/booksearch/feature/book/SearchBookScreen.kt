package com.oreocube.booksearch.feature.book

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oreocube.booksearch.R
import com.oreocube.booksearch.core.ui.theme.Gray10
import com.oreocube.booksearch.domain.model.Book

@Composable
fun SearchBookScreen() {

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
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // TODO: 도서 이미지
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(color = Gray10)
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()
        ) {
            Text(text = book.title, fontSize = 18.sp)
            Text(text = book.authors, fontSize = 14.sp)
            Text(text = "${book.publisher} · ${book.publicationYear}", fontSize = 14.sp)
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
        )
    )
}

@Composable
@Preview(showBackground = true)
private fun SearchBookScreenPreview() {
    SearchBookScreen(

    )
}
