package com.oreocube.booksearch.feature.library

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.oreocube.booksearch.domain.model.Library
import com.oreocube.booksearch.domain.model.param.LibrarySearchParam
import com.oreocube.booksearch.domain.usecase.AddFavoriteLibraryUseCase
import com.oreocube.booksearch.domain.usecase.DeleteFavoriteLibraryUseCase
import com.oreocube.booksearch.domain.usecase.GetFavoriteLibrariesUseCase
import com.oreocube.booksearch.domain.usecase.GetLibrariesByRegionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchLibraryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getLibrariesByRegionUseCase: GetLibrariesByRegionUseCase,
    getFavoriteLibrariesUseCase: GetFavoriteLibrariesUseCase,
    private val addFavoriteLibraryUseCase: AddFavoriteLibraryUseCase,
    private val deleteFavoriteLibraryUseCase: DeleteFavoriteLibraryUseCase,
) : ViewModel() {
    private val districtIdKey = "districtIdKey"

    private val searchLibraryRoute: SearchLibraryRoute = savedStateHandle.toRoute()
    private val districtId = savedStateHandle.getStateFlow(
        key = districtIdKey,
        initialValue = searchLibraryRoute.districtId,
    )

    private val favoriteLibraryIds: StateFlow<Set<String>> = getFavoriteLibrariesUseCase()
        .map { list -> list.map { it.id }.toSet() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = emptySet(),
        )

    val uiState: StateFlow<SearchLibraryUiState> = districtId
        .map { id ->
            getLibrariesByRegionUseCase(LibrarySearchParam(districtId = id))
        }.combine(favoriteLibraryIds) { libraries, favoriteSet ->
            val searchResult = libraries.map { it.copy(isFavorite = it.id in favoriteSet) }
            SearchLibraryUiState.Result(list = searchResult)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = SearchLibraryUiState.Loading,
        )

    fun toggleLibraryStar(library: Library) {
        viewModelScope.launch {
            favoriteLibraryIds.first().let { idSet ->
                if (library.id in idSet) {
                    deleteFavoriteLibraryUseCase(library.id)
                } else {
                    addFavoriteLibraryUseCase(library.toShort())
                }
            }
        }
    }
}

sealed class SearchLibraryUiState {
    data object Loading : SearchLibraryUiState()
    data class Result(
        val list: List<Library>
    ) : SearchLibraryUiState()
}
