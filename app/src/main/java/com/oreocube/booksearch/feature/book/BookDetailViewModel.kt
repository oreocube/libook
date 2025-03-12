package com.oreocube.booksearch.feature.book

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.oreocube.booksearch.domain.model.BookAvailability
import com.oreocube.booksearch.domain.model.BookDetail
import com.oreocube.booksearch.domain.model.LibraryShort
import com.oreocube.booksearch.domain.model.param.BookDetailParam
import com.oreocube.booksearch.domain.usecase.CheckBookAvailabilityUseCase
import com.oreocube.booksearch.domain.usecase.GetBookDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getBookDetailUseCase: GetBookDetailUseCase,
    checkBookAvailabilityUseCase: CheckBookAvailabilityUseCase,
) : ViewModel() {
    private val isbnKey = "isbnKey"

    private val bookDetailRoute: BookDetailRoute = savedStateHandle.toRoute()
    private val isbn13 = savedStateHandle.getStateFlow(
        key = isbnKey,
        initialValue = bookDetailRoute.isbn,
    )

    val uiState: StateFlow<BookDetailUiState> = isbn13.flatMapLatest { isbn ->
        combine(
            getBookDetailUseCase(BookDetailParam(isbn)),
            checkBookAvailabilityUseCase(isbn),
            BookDetailUiState::Data,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = BookDetailUiState.Loading
    )
}

sealed class BookDetailUiState {
    data object Loading : BookDetailUiState()
    data class Data(
        val book: BookDetail,
        val status: List<Pair<LibraryShort, BookAvailability>>
    ) : BookDetailUiState()
}
