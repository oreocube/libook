package com.oreocube.booksearch.feature.library

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.oreocube.booksearch.domain.model.Library
import com.oreocube.booksearch.domain.model.param.LibrarySearchParam
import com.oreocube.booksearch.domain.usecase.GetLibrariesByRegionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchLibraryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getLibrariesByRegionUseCase: GetLibrariesByRegionUseCase,
) : ViewModel() {
    private val districtIdKey = "districtIdKey"

    private val searchLibraryRoute: SearchLibraryRoute = savedStateHandle.toRoute()
    private val districtId = savedStateHandle.getStateFlow(
        key = districtIdKey,
        initialValue = searchLibraryRoute.districtId,
    )

    val uiState: StateFlow<SearchLibraryUiState> = districtId.map { id ->
        val searchResult = getLibrariesByRegionUseCase(LibrarySearchParam(districtId = id))
        SearchLibraryUiState.Result(
            list = searchResult,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = SearchLibraryUiState.Loading,
    )
}

sealed class SearchLibraryUiState {
    data object Loading : SearchLibraryUiState()
    data class Result(
        val list: List<Library>
    ) : SearchLibraryUiState()
}
