package com.oreocube.booksearch.core.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.oreocube.booksearch.R
import com.oreocube.booksearch.feature.book.SearchBookRoute
import com.oreocube.booksearch.feature.favorite.library.FavoriteLibraryRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val route: KClass<*>,
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int,
) {
    SEARCH_BOOK(
        route = SearchBookRoute::class,
        label = R.string.menu_search_book,
        icon = R.drawable.ic_search_24,
        selectedIcon = R.drawable.ic_search_24,
    ),
    FAVORITE_LIBRARY(
        route = FavoriteLibraryRoute::class,
        label = R.string.menu_favorite_library,
        icon = R.drawable.ic_bookmark_border_24,
        selectedIcon = R.drawable.ic_bookmark_filled_24,
    ),
    ;
}
