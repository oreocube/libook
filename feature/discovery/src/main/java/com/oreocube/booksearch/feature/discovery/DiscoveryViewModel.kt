package com.oreocube.booksearch.feature.discovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oreocube.booksearch.domain.usecase.GetAllHistoriesUseCase
import com.oreocube.booksearch.domain.usecase.GetFavoriteBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoveryViewModel @Inject constructor(
    private val getFavoriteBookUseCase: GetFavoriteBookUseCase,
    private val getAllHistoryUseCase: GetAllHistoriesUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<DiscoveryUiState>(DiscoveryUiState.initialState)
    val uiState = _uiState.asStateFlow()

    fun fetchData() {
        getFavoriteBooks()
        getRecentHistories()
    }

    private fun getFavoriteBooks() {
        viewModelScope.launch {
            runCatching {
                getFavoriteBookUseCase()
            }.onSuccess { books ->
                _uiState.update { state ->
                    state.copy(
                        favoriteBooks = books.map {
                            DiscoveryBookUiModel(
                                isbn = it.isbn,
                                title = it.title,
                                authors = it.authors,
                                imageUrl = it.imageUrl,
                            )
                        }
                    )
                }
            }
        }
    }

    private fun getRecentHistories() {
        viewModelScope.launch {
            runCatching {
                getAllHistoryUseCase()
            }.onSuccess { histories ->
                _uiState.update { state ->
                    state.copy(
                        recentBooks = histories.map {
                            DiscoveryBookUiModel(
                                isbn = it.isbn,
                                title = it.title,
                                authors = it.authors,
                                imageUrl = it.imageUrl,
                            )
                        }
                    )
                }
            }
        }
    }
}
