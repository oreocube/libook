package com.oreocube.booksearch.feature.favorite.library

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
import com.oreocube.booksearch.domain.model.LibraryShort

@Composable
fun FavoriteLibraryRoute(
    viewModel: FavoriteLibraryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FavoriteLibraryScreen(
        uiState = uiState,
        onStarClick = viewModel::deleteFavoriteLibrary,
    )
}

@Composable
private fun FavoriteLibraryScreen(
    uiState: FavoriteLibraryUiState,
    onStarClick: (String) -> Unit,
) {
    when (uiState) {
        is FavoriteLibraryUiState.Data -> {
            FavoriteLibraryList(
                modifier = Modifier.fillMaxSize(),
                libraries = uiState.libraries,
                onStarClick = onStarClick,
            )
        }

        else -> {}
    }
}

@Composable
fun FavoriteLibraryList(
    modifier: Modifier = Modifier,
    libraries: List<LibraryShort>,
    onStarClick: (String) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        libraries.forEach { library ->
            item(key = library.id) {
                FavoriteLibraryItem(
                    library = library,
                    onStarClick = { onStarClick(library.id) },
                )
            }
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
fun FavoriteLibraryScreenPreview() {
    FavoriteLibraryScreen(
        uiState = FavoriteLibraryUiState.Data(
            libraries = listOf(
                LibraryShort(id = "11", name = "자바도서관"),
                LibraryShort(id = "12", name = "코틀린도서관"),
            )
        ),
        onStarClick = {},
    )
}
