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
import com.oreocube.booksearch.feature.library.model.LibraryUiState
import com.oreocube.booksearch.feature.library.model.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
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

    private val _eventChannel = Channel<SearchLibraryUiEvent>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()

    private val favoriteLibraryIds: StateFlow<PersistentSet<String>> = getFavoriteLibrariesUseCase()
        .map { list -> list.map { it.id }.toPersistentSet() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = persistentSetOf(),
        )

    val uiState: StateFlow<SearchLibraryUiState> = districtId
        .map { id ->
            getLibrariesByRegionUseCase(LibrarySearchParam(districtId = id))
                .map(Library::toUiState)
                .toPersistentList()
        }.combine(favoriteLibraryIds) { libraries, favoriteSet ->
            SearchLibraryUiState(
                isLoading = false,
                list = libraries,
                favoriteIds = favoriteSet,
            )
        }.catch {
            _eventChannel.send(SearchLibraryUiEvent.Error("도서관을 불러오는데 실패했습니다."))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = SearchLibraryUiState.initialState,
        )

    fun toggleLibraryStar(library: LibraryUiState) {
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

data class SearchLibraryUiState(
    val isLoading: Boolean,
    val list: ImmutableList<LibraryUiState>,
    val favoriteIds: PersistentSet<String>,
) {
    companion object {
        val initialState = SearchLibraryUiState(
            isLoading = true,
            list = persistentListOf(),
            favoriteIds = persistentSetOf(),
        )
    }
}

sealed class SearchLibraryUiEvent {
    data class Error(val message: String) : SearchLibraryUiEvent()
}
