package com.oreocube.booksearch.core.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.oreocube.booksearch.core.navigation.TopLevelDestination
import com.oreocube.booksearch.core.ui.theme.BooksearchTheme
import com.oreocube.booksearch.core.ui.theme.Brown20

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
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = BookSearchNavigationDefaults.navigationSelectedItemColor,
            unselectedIconColor = BookSearchNavigationDefaults.navigationContentColor,
            selectedTextColor = BookSearchNavigationDefaults.navigationContentColor,
            unselectedTextColor = BookSearchNavigationDefaults.navigationContentColor,
            indicatorColor = BookSearchNavigationDefaults.navigationIndicatorColor,
        )
    )
}

object BookSearchNavigationDefaults {
    val containerColor = Color.White
    val navigationSelectedItemColor = Color.White
    val navigationContentColor = Brown20
    val navigationIndicatorColor = Brown20
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
