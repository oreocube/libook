package com.oreocube.booksearch.feature.favorite.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oreocube.booksearch.domain.model.LibraryShort
import com.oreocube.booksearch.domain.usecase.DeleteFavoriteLibraryUseCase
import com.oreocube.booksearch.domain.usecase.GetFavoriteLibrariesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteLibraryViewModel @Inject constructor(
    getFavoriteLibrariesUseCase: GetFavoriteLibrariesUseCase,
    private val deleteFavoriteLibraryUseCase: DeleteFavoriteLibraryUseCase,
) : ViewModel() {
    val uiState: StateFlow<FavoriteLibraryUiState> = getFavoriteLibrariesUseCase()
        .map { libraries ->
            FavoriteLibraryUiState.Data(
                libraries = libraries,
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = FavoriteLibraryUiState.Loading,
        )

    fun deleteFavoriteLibrary(id: String) {
        viewModelScope.launch {
            deleteFavoriteLibraryUseCase(id = id)
        }
    }
}

sealed class FavoriteLibraryUiState {
    data object Loading : FavoriteLibraryUiState()
    data class Data(
        val libraries: List<LibraryShort>,
    ) : FavoriteLibraryUiState()
}
