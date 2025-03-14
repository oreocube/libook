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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oreocube.booksearch.R
import com.oreocube.booksearch.core.ui.theme.Brown20
import com.oreocube.booksearch.domain.model.LibraryShort

@Composable
fun FavoriteLibraryRoute() {

}

@Composable
fun FavoriteLibraryScreen(
    libraries: List<LibraryShort>,
    onStarClick: (LibraryShort) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        libraries.forEach { library ->
            item(key = library.id) {
                FavoriteLibraryItem(
                    library = library,
                    onStarClick = onStarClick,
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
        libraries = listOf(
            LibraryShort(id = "11", name = "자바도서관"),
            LibraryShort(id = "12", name = "코틀린도서관"),
        ),
        onStarClick = {},
    )
}
