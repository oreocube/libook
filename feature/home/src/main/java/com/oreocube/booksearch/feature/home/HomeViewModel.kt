package com.oreocube.booksearch.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oreocube.booksearch.domain.model.TrendingBook
import com.oreocube.booksearch.domain.usecase.AnonymousLoginUseCase
import com.oreocube.booksearch.domain.usecase.GetTrendingBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val anonymousLoginUseCase: AnonymousLoginUseCase,
    private val getTrendingBooksUseCase: GetTrendingBooksUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        anonymousLogin()
        getTrendingBooks()
    }

    private fun anonymousLogin() {
        viewModelScope.launch {
            runCatching {
                anonymousLoginUseCase()
            }
        }
    }

    private fun getTrendingBooks() {
        viewModelScope.launch {
            runCatching {
                getTrendingBooksUseCase()
            }.onSuccess { trendingBooks ->
                _uiState.update { state ->
                    state.copy(trendingBooks = trendingBooks)
                }
            }
        }
    }
}

data class HomeUiState(
    val trendingBooks: List<TrendingBook>? = null,
)
