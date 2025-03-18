package com.oreocube.booksearch.feature.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oreocube.booksearch.R
import com.oreocube.booksearch.core.ui.component.BookSearchTopBar
import com.oreocube.booksearch.core.ui.theme.Brown20
import com.oreocube.booksearch.core.ui.theme.Gray10
import com.oreocube.booksearch.core.ui.theme.Gray20
import com.oreocube.booksearch.domain.model.Library

@Composable
fun SearchLibraryRoute(
    onBackClick: () -> Unit,
    viewModel: SearchLibraryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SearchLibraryScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onStarClick = viewModel::toggleLibraryStar,
    )
}

@Composable
fun SearchLibraryScreen(
    uiState: SearchLibraryUiState,
    onBackClick: () -> Unit,
    onStarClick: (Library) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        BookSearchTopBar(
            title = stringResource(R.string.menu_search_library_result),
            description = stringResource(R.string.menu_description_search_library_result),
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
            is SearchLibraryUiState.Result -> {
                if (uiState.list.isEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        text = stringResource(R.string.no_result),
                        color = Gray20,
                    )
                } else {
                    LazyColumn {
                        items(
                            items = uiState.list,
                            key = { library -> library.id }
                        ) { library ->
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
