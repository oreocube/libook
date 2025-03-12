package com.oreocube.booksearch.feature.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oreocube.booksearch.domain.model.Book
import com.oreocube.booksearch.domain.usecase.SearchBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchBookViewModel @Inject constructor(
    private val searchBooksUseCase: SearchBooksUseCase,
) : ViewModel() {
    private val _query = MutableStateFlow("")
    private val _searchResult = _query
        .debounce(700)
        .map { query ->
            query.takeIf { it.length > 1 }
                ?.let { searchBooksUseCase(it) }
                ?: emptyList()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = emptyList()
        )

    val uiState: StateFlow<SearchBookUiState> = combine(_query, _searchResult) { query, result ->
        SearchBookUiState(query = query, result = result)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = SearchBookUiState()
    )

    fun onInputChanged(input: String = "") {
        _query.value = input
    }
}

data class SearchBookUiState(
    val query: String = "",
    val result: List<Book> = emptyList(),
)
