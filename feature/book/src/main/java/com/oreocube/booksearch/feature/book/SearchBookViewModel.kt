package com.oreocube.booksearch.feature.book

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oreocube.booksearch.domain.model.Book
import com.oreocube.booksearch.domain.model.RecentBookHistory
import com.oreocube.booksearch.domain.usecase.AddHistoryUseCase
import com.oreocube.booksearch.domain.usecase.SearchBooksUseCase
import com.oreocube.booksearch.feature.book.model.BookUiState
import com.oreocube.booksearch.feature.book.model.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchBookViewModel @Inject constructor(
    private val searchBooksUseCase: SearchBooksUseCase,
    private val addHistoryUseCase: AddHistoryUseCase,
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
        SearchBookUiState(
            query = query,
            result = result.toImmutableList(),
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = SearchBookUiState()
    )

    fun onInputChanged(input: String = "") {
        _query.value = input
    }

    private suspend fun searchBookSafely(query: String): List<BookUiState> {
        return runCatching {
            searchBooksUseCase(query).map(Book::toUiState)
        }.getOrElse {
            _eventChannel.send(SearchBookUiEvent.Error("도서 검색에 실패했습니다."))
            emptyList()
        }
    }

    fun onBookClicked(book: BookUiState) {
        viewModelScope.launch {
            val history = RecentBookHistory(
                isbn = book.isbn13,
                title = book.title,
                searchedAt = System.currentTimeMillis(),
            )
            runCatching {
                addHistoryUseCase(item = history)
            }.onSuccess {
                Log.d("TAG", "onBookClicked: 성공")
            }.onFailure {
                Log.d("TAG", "onBookClicked: 실패")
            }
            _eventChannel.send(SearchBookUiEvent.NavigateToBookDetail(isbn = book.isbn13))
        }
    }
}

data class SearchBookUiState(
    val query: String = "",
    val result: ImmutableList<BookUiState> = persistentListOf(),
)

sealed class SearchBookUiEvent {
    data class Error(val message: String) : SearchBookUiEvent()
    data class NavigateToBookDetail(val isbn: String) : SearchBookUiEvent()
}
