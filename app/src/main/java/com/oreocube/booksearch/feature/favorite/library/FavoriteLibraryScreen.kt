package com.oreocube.booksearch.feature.favorite.library

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.oreocube.booksearch.core.ui.theme.Gray20
import com.oreocube.booksearch.domain.model.LibraryShort

@Composable
fun FavoriteLibraryRoute(
    onSearchClick: () -> Unit,
    viewModel: FavoriteLibraryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FavoriteLibraryScreen(
        uiState = uiState,
        onStarClick = viewModel::deleteFavoriteLibrary,
        onSearchClick = onSearchClick,
    )
}

@Composable
private fun FavoriteLibraryScreen(
    uiState: FavoriteLibraryUiState,
    onSearchClick: () -> Unit,
    onStarClick: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        BookSearchTopBar(
            title = stringResource(R.string.menu_favorite_library),
            description = stringResource(R.string.menu_description_favorite_library),
            menuIcon = {
                IconButton(onClick = onSearchClick) {
                    Icon(
                        painter = painterResource(R.drawable.ic_search_24),
                        contentDescription = stringResource(R.string.menu_search_library)
                    )
                }
            }
        )
        when (uiState) {
            is FavoriteLibraryUiState.Data -> {
                if (uiState.libraries.isEmpty()) {
                    EmptyFavoriteLibraryContent(
                        onSearchClick = onSearchClick,
                    )
                } else {
                    FavoriteLibraryList(
                        modifier = Modifier.fillMaxSize(),
                        libraries = uiState.libraries,
                        onStarClick = onStarClick,
                    )
                }
            }

            else -> {}
        }
    }
}

@Composable
private fun EmptyFavoriteLibraryContent(
    onSearchClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.no_favorite_library),
            style = MaterialTheme.typography.bodyMedium,
            color = Gray20,
        )
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .clip(shape = RoundedCornerShape(32.dp))
                .background(color = Brown20)
                .clickable(onClick = onSearchClick)
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
private fun FavoriteLibraryList(
    modifier: Modifier = Modifier,
    libraries: List<LibraryShort>,
    onStarClick: (String) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(
            items = libraries,
            key = { library -> library.id },
        ) { library ->
            FavoriteLibraryItem(
                library = library,
                onStarClick = { onStarClick(library.id) },
            )
        }
    }
}

@Composable
private fun FavoriteLibraryItem(
    modifier: Modifier = Modifier,
    library: LibraryShort,
    onStarClick: (LibraryShort) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = library.name,
            color = Color.Black,
            fontSize = 18.sp,
        )
        IconButton(
            onClick = { onStarClick(library) }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_bookmark_filled_24),
                contentDescription = null,
                tint = Brown20,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun FavoriteLibraryScreenPreview1() {
    FavoriteLibraryScreen(
        uiState = FavoriteLibraryUiState.Data(
            libraries = listOf(
                LibraryShort(id = "11", name = "자바도서관"),
                LibraryShort(id = "12", name = "코틀린도서관"),
            )
        ),
        onSearchClick = {},
        onStarClick = {},
    )
}

@Composable
@Preview(showBackground = true)
private fun FavoriteLibraryScreenPreview2() {
    FavoriteLibraryScreen(
        uiState = FavoriteLibraryUiState.Data(
            libraries = emptyList()
        ),
        onSearchClick = {},
        onStarClick = {},
    )
}
