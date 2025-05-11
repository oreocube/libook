package com.oreocube.booksearch.feature.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.oreocube.booksearch.domain.model.Book
import com.oreocube.booksearch.domain.model.RecentBookHistory
import com.oreocube.booksearch.domain.usecase.AddHistoryUseCase
import com.oreocube.booksearch.domain.usecase.ClearHistoryUseCase
import com.oreocube.booksearch.domain.usecase.DeleteHistoryUseCase
import com.oreocube.booksearch.domain.usecase.GetAllHistoriesUseCase
import com.oreocube.booksearch.domain.usecase.SearchBooksUseCase
import com.oreocube.booksearch.feature.book.model.BookUiState
import com.oreocube.booksearch.feature.book.model.RecentHistoryUiState
import com.oreocube.booksearch.feature.book.model.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchBookViewModel @Inject constructor(
    private val searchBooksUseCase: SearchBooksUseCase,
    private val addHistoryUseCase: AddHistoryUseCase,
    private val getAllHistoriesUseCase: GetAllHistoriesUseCase,
    private val deleteHistoryUseCase: DeleteHistoryUseCase,
    private val clearHistoryUseCase: ClearHistoryUseCase,
) : ViewModel() {
    private val _eventChannel = Channel<SearchBookUiEvent>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()

    private val _query = MutableStateFlow("")
    private val _recentHistories = MutableStateFlow<List<RecentBookHistory>>(emptyList())

    private val _searchResult = _query
        .onEach { query ->
            if (query.isBlank()) refreshHistory()
        }
        .debounce(700)
        .map { query ->
            if (query.length > 1) searchBooksUseCase(query)
                .map { pagingData -> pagingData.map(Book::toUiState) }
                .cachedIn(viewModelScope)
            else emptyFlow()
        }

    val uiState: StateFlow<SearchBookUiState> = combine(
        _query, _searchResult, _recentHistories
    ) { query, result, history ->
        SearchBookUiState(
            query = query,
            result = result,
            recentHistory = history.map(RecentBookHistory::toUiState).toImmutableList(),
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = SearchBookUiState()
    )

    private fun onInputChanged(input: String = "") {
        _query.value = input
    }

    private fun refreshHistory() {
        viewModelScope.launch {
            runCatching {
                getAllHistoriesUseCase()
            }.onSuccess { history ->
                _recentHistories.value = history
            }
        }
    }

    private fun addHistory(title: String, isbn: String) {
        val newHistory = RecentBookHistory(
            isbn = isbn,
            title = title,
            searchedAt = System.currentTimeMillis(),
        )
        viewModelScope.launch {
            runCatching {
                addHistoryUseCase(newHistory)
            }
        }
    }

    private fun onBookClicked(book: BookUiState) {
        viewModelScope.launch {
            addHistory(title = book.title, isbn = book.isbn13)
            _eventChannel.send(SearchBookUiEvent.NavigateToBookDetail(isbn = book.isbn13))
        }
    }

    private fun onHistoryItemClick(history: RecentHistoryUiState) {
        viewModelScope.launch {
            addHistory(title = history.title, isbn = history.isbn)
            _eventChannel.send(SearchBookUiEvent.NavigateToBookDetail(isbn = history.isbn))
        }
    }

    private fun onDeleteHistoryClick(history: RecentHistoryUiState) {
        viewModelScope.launch {
            runCatching {
                deleteHistoryUseCase(history.isbn)
                _recentHistories.update { it.filterNot { h -> h.isbn == history.isbn } }
            }
        }
    }

    private fun onClearHistoryClick() {
        viewModelScope.launch {
            runCatching {
                clearHistoryUseCase()
                _recentHistories.value = emptyList()
            }
        }
    }

    fun onAction(action: SearchBookUiAction) {
        when (action) {
            is SearchBookUiAction.InputChanged -> onInputChanged(action.input)
            is SearchBookUiAction.BookClicked -> onBookClicked(action.book)
            is SearchBookUiAction.HistoryItemClick -> onHistoryItemClick(action.history)
            is SearchBookUiAction.DeleteHistoryClick -> onDeleteHistoryClick(action.history)
            is SearchBookUiAction.ClearHistoryClick -> onClearHistoryClick()
        }
    }
}
