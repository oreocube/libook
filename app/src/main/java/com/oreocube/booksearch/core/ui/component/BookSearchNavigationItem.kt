package com.oreocube.booksearch.core.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.oreocube.booksearch.core.navigation.TopLevelDestination
import com.oreocube.booksearch.core.ui.theme.BooksearchTheme

@Composable
fun RowScope.BookSearchNavigationItem(
    destination: TopLevelDestination,
    selected: Boolean,
    onClick: () -> Unit,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            if (selected) {
                Icon(
                    painter = painterResource(destination.selectedIcon),
                    contentDescription = stringResource(destination.label),
                )
            } else {
                Icon(
                    painter = painterResource(destination.icon),
                    contentDescription = stringResource(destination.label),
                )
            }
        },
        label = { Text(text = stringResource(destination.label)) },
        alwaysShowLabel = true,
    )
}

@Composable
@Preview(showBackground = true)
private fun BookSearchNavigationItemPreview() {
    val topLevelDestination = TopLevelDestination.entries

    BooksearchTheme {
        Row {
            topLevelDestination.forEach { destination ->
                BookSearchNavigationItem(
                    destination = destination,
                    selected = destination == TopLevelDestination.SEARCH_BOOK,
                    onClick = {},
                )
            }
        }
    }
}
