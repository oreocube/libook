package com.oreocube.booksearch.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.oreocube.booksearch.core.ui.R
import com.oreocube.booksearch.feature.discovery.DiscoveryRoute
import com.oreocube.booksearch.feature.favorite.FavoriteLibraryRoute
import com.oreocube.booksearch.feature.home.HomeRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val route: KClass<*>,
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int,
) {
    HOME(
        route = HomeRoute::class,
        label = R.string.menu_search_book,
        icon = R.drawable.ic_search_24,
        selectedIcon = R.drawable.ic_search_24,
    ),
    DISCOVERY(
        route = DiscoveryRoute::class,
        label = R.string.menu_discovery,
        icon = R.drawable.ic_awesome_border_24,
        selectedIcon = R.drawable.ic_awesome_filled_24,
    ),
    FAVORITE_LIBRARY(
        route = FavoriteLibraryRoute::class,
        label = R.string.menu_favorite_library,
        icon = R.drawable.ic_bookmark_border_24,
        selectedIcon = R.drawable.ic_bookmark_filled_24,
    ),
    ;
}
