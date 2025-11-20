package com.oreocube.booksearch.feature.book.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.oreocube.booksearch.domain.model.Book
import com.oreocube.booksearch.domain.model.BookInfo
import com.oreocube.booksearch.domain.model.RecentBookHistory
import com.oreocube.booksearch.domain.usecase.AddHistoryUseCase
import com.oreocube.booksearch.domain.usecase.ClearHistoryUseCase
import com.oreocube.booksearch.domain.usecase.DeleteHistoryUseCase
import com.oreocube.booksearch.domain.usecase.GetAllHistoriesUseCase
import com.oreocube.booksearch.domain.usecase.SearchBooksUseCase
import com.oreocube.booksearch.feature.book.model.BookUiState
import com.oreocube.booksearch.feature.book.model.RecentHistoryUiState
import com.oreocube.booksearch.feature.book.model.toBook
import com.oreocube.booksearch.feature.book.model.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
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

    private val _uiState = MutableStateFlow(SearchBookUiState())
    val uiState: StateFlow<SearchBookUiState> = _uiState.asStateFlow()

    init {
        observeQuery()
        refreshHistory()
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

    private fun observeQuery() {
        uiState.map { it.query }
            .debounce(700)
            .distinctUntilChanged()
            .onEach { query -> updateSearchResult(query) }
            .launchIn(viewModelScope)
    }

    private suspend fun updateSearchResult(query: String) {
        val result = if (query.length > 1) searchBooksUseCase(query)
            .map { pagingData -> pagingData.map(Book::toUiState) }
            .cachedIn(viewModelScope)
        else emptyFlow()

        _uiState.update { state -> state.copy(result = result) }
    }

    private fun onInputChanged(input: String = "") {
        if (input.isBlank()) refreshHistory()
        _uiState.update { state -> state.copy(query = input) }
    }

    private fun refreshHistory() {
        viewModelScope.launch {
            runCatching {
                getAllHistoriesUseCase()
            }.onSuccess { history ->
                updateRecentHistory(history.map(RecentBookHistory::toUiState))
            }
        }
    }

    private fun addHistory(book: BookInfo) {
        val newHistory = RecentBookHistory(
            isbn = book.isbn,
            title = book.title,
            authors = book.authors,
            imageUrl = book.imageUrl,
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
            addHistory(book.toBook())
            _eventChannel.send(SearchBookUiEvent.NavigateToBookDetail(isbn = book.isbn13))
        }
    }

    private fun onHistoryItemClick(history: RecentHistoryUiState) {
        viewModelScope.launch {
            addHistory(history.toBook())
            _eventChannel.send(SearchBookUiEvent.NavigateToBookDetail(isbn = history.isbn))
        }
    }

    private fun onDeleteHistoryClick(history: RecentHistoryUiState) {
        viewModelScope.launch {
            runCatching {
                deleteHistoryUseCase(history.isbn)
                val newHistory = uiState.value.recentHistory
                    .filterNot { h -> h.isbn == history.isbn }
                updateRecentHistory(newHistory)
            }
        }
    }

    private fun onClearHistoryClick() {
        viewModelScope.launch {
            runCatching {
                clearHistoryUseCase()
                updateRecentHistory(emptyList())
            }
        }
    }

    private fun updateRecentHistory(history: List<RecentHistoryUiState>) {
        _uiState.update { state ->
            state.copy(recentHistory = history.toImmutableList())
        }
    }
}
