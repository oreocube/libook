package com.oreocube.booksearch.feature.book

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.oreocube.booksearch.domain.model.BookAvailability
import com.oreocube.booksearch.domain.model.BookDetail
import com.oreocube.booksearch.domain.model.LibraryShort
import com.oreocube.booksearch.domain.model.RecommendedBook
import com.oreocube.booksearch.domain.model.param.BookDetailParam
import com.oreocube.booksearch.domain.usecase.CheckBookAvailabilityUseCase
import com.oreocube.booksearch.domain.usecase.GetBookDetailUseCase
import com.oreocube.booksearch.domain.usecase.GetRecommendedBooksWithTargetBookUseCase
import com.oreocube.booksearch.feature.book.model.RecommendedBookUiState
import com.oreocube.booksearch.feature.book.model.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val getBookDetailUseCase: GetBookDetailUseCase,
    val checkBookAvailabilityUseCase: CheckBookAvailabilityUseCase,
    val getRecommendedBooksUseCase: GetRecommendedBooksWithTargetBookUseCase,
) : ViewModel() {
    private val isbnKey = "isbnKey"

    private val bookDetailRoute: BookDetailRoute = savedStateHandle.toRoute()
    private val isbn13 = savedStateHandle.getStateFlow(
        key = isbnKey,
        initialValue = bookDetailRoute.isbn,
    )

    private val _uiState = MutableStateFlow(BookDetailUiState.initialState)
    val uiState: StateFlow<BookDetailUiState> = _uiState.asStateFlow()

    init {
        getBookDetail(isbn13.value)
        getBookAvailability(isbn13.value)
        getRecommendedBooks(isbn13.value)
    }

    private val _eventChannel = Channel<BookDetailUiEvent>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()

    private fun getBookDetail(isbn: String) {
        viewModelScope.launch {
            runCatching {
                getBookDetailUseCase(BookDetailParam(isbn))
            }.onSuccess { bookDetail ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        book = bookDetail,
                    )
                }
            }.onFailure {
                _eventChannel.send(BookDetailUiEvent.Error("도서 정보를 불러오는데 실패했습니다."))
            }
        }
    }

    private fun getBookAvailability(isbn: String) {
        viewModelScope.launch {
            runCatching {
                checkBookAvailabilityUseCase(isbn)
            }.onSuccess { availability ->
                _uiState.update { state ->
                    state.copy(
                        status = availability,
                    )
                }
            }
        }
    }

    private fun getRecommendedBooks(isbn: String) {
        viewModelScope.launch {
            runCatching {
                getRecommendedBooksUseCase(isbn)
            }.onSuccess { books ->
                val recommendedBooks = books.map(RecommendedBook::toUiState)
                _uiState.update { state ->
                    state.copy(
                        recommendBooks = recommendedBooks,
                    )
                }
            }
        }
    }
}

data class BookDetailUiState(
    val isLoading: Boolean,
    val book: BookDetail?,
    val status: List<Pair<LibraryShort, BookAvailability>>,
    val recommendBooks: List<RecommendedBookUiState> = emptyList(),
) {
    companion object {
        val initialState = BookDetailUiState(
            isLoading = true,
            book = null,
            status = emptyList(),
            recommendBooks = emptyList(),
        )
    }
}

sealed class BookDetailUiEvent {
    data class Error(val message: String) : BookDetailUiEvent()
}
