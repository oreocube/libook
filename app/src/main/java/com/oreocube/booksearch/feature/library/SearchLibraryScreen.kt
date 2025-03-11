package com.oreocube.booksearch.feature.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oreocube.booksearch.R
import com.oreocube.booksearch.core.ui.theme.Brown20
import com.oreocube.booksearch.core.ui.theme.Gray10
import com.oreocube.booksearch.domain.model.Library

@Composable
fun SearchLibraryRoute(
    viewModel: SearchLibraryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SearchLibraryScreen(
        uiState = uiState,
        onStarClick = {},
    )
}

@Composable
fun SearchLibraryScreen(
    uiState: SearchLibraryUiState,
    onStarClick: (Library) -> Unit,
) {
    when (uiState) {
        is SearchLibraryUiState.Result -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                uiState.list.forEach { library ->
                    item {
                        LibraryItem(
                            modifier = Modifier.fillMaxWidth(),
                            library = library,
                            onStarClick = onStarClick,
                        )
                    }
                }
            }
        }

        else -> {}
    }
}

@Composable
private fun LibraryItem(
    modifier: Modifier = Modifier,
    library: Library,
    onStarClick: (Library) -> Unit,
) {
    val iconRes = remember(library.isFavorite) {
        if (library.isFavorite) R.drawable.ic_bookmark_filled_24
        else R.drawable.ic_bookmark_border_24
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = library.name,
                color = Color.Black,
                fontSize = 18.sp,
            )
            Text(
                text = library.address,
                color = Gray10,
                fontSize = 14.sp,
            )
        }
        IconButton(
            onClick = { onStarClick(library) }
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = Brown20,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun LibraryItemPreview() {
    LibraryItem(
        library = Library(
            id = "11111",
            name = "도서관이름",
            address = "서울특별시 광진구",
            tel = "02-1234-5678",
            fax = "02-1234-5678",
            latitude = "",
            longitude = "",
            homepageUrl = "",
            closedTime = "매주 월요일",
            operatingTime = "평일 09:00~22:00",
            bookCount = 11000,
        ),
        onStarClick = {},
    )
}
