package com.oreocube.booksearch.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oreocube.booksearch.R
import com.oreocube.booksearch.core.ui.theme.Gray20

private val topAppBarHeight = 80.dp

@Composable
fun BookSearchTopBar(
    modifier: Modifier = Modifier,
    title: String,
    description: String = "",
    menuIcon: @Composable () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(topAppBarHeight)
            .background(color = Color.White)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
            )
            if (description.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray20,
                )
            }
        }
        menuIcon()
    }
}

@Composable
@Preview
private fun BookSearchTopBarPreview1() {
    BookSearchTopBar(
        title = "도서 검색",
    )
}

@Composable
@Preview
private fun BookSearchTopBarPreview2() {
    BookSearchTopBar(
        title = "관심 도서관",
        description = "자주 가는 도서관을 저장할 수 있어요",
        menuIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_search_24),
                contentDescription = stringResource(R.string.menu_search_library)
            )
        }
    )
}
