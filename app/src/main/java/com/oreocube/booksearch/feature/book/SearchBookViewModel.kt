package com.oreocube.booksearch.feature.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oreocube.booksearch.domain.model.Book
import com.oreocube.booksearch.domain.usecase.SearchBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchBookViewModel @Inject constructor(
    private val searchBooksUseCase: SearchBooksUseCase,
) : ViewModel() {
    private val _eventChannel = Channel<SearchBookUiEvent>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()

    private val _query = MutableStateFlow("")
    private val _searchResult = _query
        .debounce(700)
        .map { query ->
            if (query.length > 1) searchBookSafely(query) else emptyList()
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

    private suspend fun searchBookSafely(query: String): List<Book> {
        return runCatching {
            searchBooksUseCase(query)
        }.getOrElse {
            _eventChannel.send(SearchBookUiEvent.Error("도서 검색에 실패했습니다."))
            emptyList()
        }
    }
}

data class SearchBookUiState(
    val query: String = "",
    val result: List<Book> = emptyList(),
)

sealed class SearchBookUiEvent {
    data class Error(val message: String) : SearchBookUiEvent()
}
