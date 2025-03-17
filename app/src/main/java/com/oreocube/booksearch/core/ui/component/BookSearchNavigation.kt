package com.oreocube.booksearch.core.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import com.oreocube.booksearch.core.navigation.TopLevelDestination
import com.oreocube.booksearch.core.ui.theme.BooksearchTheme
import com.oreocube.booksearch.core.ui.theme.Brown20

@Composable
fun BookSearchNavigationBar(
    isVisible: Boolean,
    content: @Composable RowScope.() -> Unit,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideIn { IntOffset(0, it.height) },
        exit = fadeOut() + slideOut { IntOffset(0, it.height) }
    ) {
        NavigationBar(
            content = content,
            containerColor = BookSearchNavigationDefaults.containerColor,
        )
    }
}

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
                    selected = destination == TopLevelDestination.HOME,
                    onClick = {},
                )
            }
        }
    }
}
