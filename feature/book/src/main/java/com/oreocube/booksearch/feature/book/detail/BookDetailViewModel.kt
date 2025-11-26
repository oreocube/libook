package com.oreocube.booksearch.feature.book.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.oreocube.booksearch.domain.model.BookInfo
import com.oreocube.booksearch.domain.model.LibraryShort
import com.oreocube.booksearch.domain.model.RecommendedBook
import com.oreocube.booksearch.domain.model.param.BookDetailParam
import com.oreocube.booksearch.domain.model.param.BookNotificationTarget
import com.oreocube.booksearch.domain.usecase.AddFavoriteBookUseCase
import com.oreocube.booksearch.domain.usecase.CheckBookAvailabilityUseCase
import com.oreocube.booksearch.domain.usecase.CheckFavoriteBookUserCase
import com.oreocube.booksearch.domain.usecase.DeleteFavoriteBookUseCase
import com.oreocube.booksearch.domain.usecase.GetAllNotificationsUseCase
import com.oreocube.booksearch.domain.usecase.GetBookDetailUseCase
import com.oreocube.booksearch.domain.usecase.GetFavoriteLibrariesUseCase
import com.oreocube.booksearch.domain.usecase.GetRecommendedBooksWithTargetBookUseCase
import com.oreocube.booksearch.domain.usecase.RegisterNotificationForBookStatusUseCase
import com.oreocube.booksearch.domain.usecase.UnregisterNotificationForBookStatusUseCase
import com.oreocube.booksearch.feature.book.model.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getBookDetailUseCase: GetBookDetailUseCase,
    private val checkFavoriteBookUserCase: CheckFavoriteBookUserCase,
    private val addFavoriteBookUseCase: AddFavoriteBookUseCase,
    private val deleteFavoriteBookUseCase: DeleteFavoriteBookUseCase,
    private val getFavoriteLibrariesUseCase: GetFavoriteLibrariesUseCase,
    private val checkBookAvailabilityUseCase: CheckBookAvailabilityUseCase,
    private val getAllNotificationsUseCase: GetAllNotificationsUseCase,
    private val registerNotificationForBookStatusUseCase: RegisterNotificationForBookStatusUseCase,
    private val unregisterNotificationForBookStatusUseCase: UnregisterNotificationForBookStatusUseCase,
    private val getRecommendedBooksUseCase: GetRecommendedBooksWithTargetBookUseCase,
) : ViewModel() {
    private val bookDetailRoute: BookDetailRoute = savedStateHandle.toRoute()
    private val isbn13 = bookDetailRoute.isbn

    private val _uiState = MutableStateFlow(BookDetailUiState.initialState)
    val uiState: StateFlow<BookDetailUiState> = _uiState.asStateFlow()

    init {
        getBookDetail(isbn13)
        checkFavoriteBook()
        getBookAvailability()
        getRecommendedBooks(isbn13)
    }

    private val _sideEffect = Channel<BookDetailSideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    fun submitIntent(intent: BookDetailIntent) {
        when (intent) {
            BookDetailIntent.EnterScreen -> checkFavoriteLibraryChanged()
            BookDetailIntent.AddLibraryClick -> postSideEffect(BookDetailSideEffect.NavigateToAddLibrary)
            BookDetailIntent.ToggleHeart -> toggleFavoriteBook()

            is BookDetailIntent.RefreshBookAvailability -> refreshBookAvailability(intent.library)
            is BookDetailIntent.ToggleNotification -> {
                toggleNotification(intent.isRegistered, intent.library)
            }

            is BookDetailIntent.BookItemClick -> {
                postSideEffect(BookDetailSideEffect.NavigateToBookDetail(intent.isbn))
            }
        }
    }

    private fun postSideEffect(event: BookDetailSideEffect) {
        viewModelScope.launch { _sideEffect.send(event) }
    }

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
                postSideEffect(BookDetailSideEffect.Error("도서 정보를 불러오는데 실패했습니다."))
            }
        }
    }

    private fun checkFavoriteBook() {
        viewModelScope.launch {
            runCatching {
                checkFavoriteBookUserCase(isbn13)
            }.onSuccess { isFavorite ->
                _uiState.update { state ->
                    state.copy(
                        isFavorite = isFavorite
                    )
                }
            }
        }
    }

    private fun toggleFavoriteBook() {
        val isFavorite = uiState.value.isFavorite
        val book = uiState.value.book ?: return
        viewModelScope.launch {
            runCatching {
                if (isFavorite) {
                    deleteFavoriteBookUseCase(isbn13)
                } else {
                    addFavoriteBookUseCase(
                        book = BookInfo(
                            isbn = isbn13,
                            title = book.title,
                            authors = book.authors,
                            imageUrl = book.imageUrl,
                        )
                    )
                }
            }.onSuccess {
                _uiState.update { state ->
                    state.copy(
                        isFavorite = !isFavorite
                    )
                }
            }
        }
    }

    private fun getBookAvailability(libraries: List<LibraryShort>? = null) {
        viewModelScope.launch {
            val notificationsDeferred = getAllNotificationsDeferred()
            val availabilityDeferred = async {
                runCatching {
                    val list = libraries ?: getFavoriteLibrariesUseCase().first()
                    checkBookAvailabilityUseCase(isbn13, list)
                }.getOrDefault(emptyList())
            }
            val notifications = notificationsDeferred.await()
            val availability = availabilityDeferred.await()
            val status = availability.map {
                it.toUiState(
                    isNotificationRegistered = notifications.contains(it.library.id)
                )
            }
            _uiState.update { state ->
                state.copy(
                    status = status,
                )
            }
        }
    }

    private fun CoroutineScope.getAllNotificationsDeferred() = async {
        runCatching { getAllNotificationsUseCase() }
            .getOrDefault(emptyList())
            .filter { it.isbn == isbn13 }
            .map { it.libraryId }
            .toSet()
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

    private fun checkFavoriteLibraryChanged() {
        val current = uiState.value
        if (current.isFirstEntry) {
            _uiState.update { it.copy(isFirstEntry = false) }
            return
        }
        viewModelScope.launch {
            val oldIds = current.status.map { it.library.id }
            val new = getFavoriteLibrariesUseCase().first()
            val newIds = new.map { it.id }
            if (oldIds != newIds) {
                getBookAvailability(new)
            }
        }
    }

    private fun refreshBookAvailability(library: LibraryShort) {
        viewModelScope.launch {
            val notificationsDeferred = getAllNotificationsDeferred()
            val availabilityDeferred = async {
                checkBookAvailabilityUseCase(isbn13, library)
            }

            val notifications = notificationsDeferred.await()
            runCatching {
                availabilityDeferred.await()
            }.onSuccess { availability ->
                _uiState.update { state ->
                    state.copy(
                        status = state.status.map { origin ->
                            if (origin.library.id == library.id) {
                                availability.toUiState(
                                    isNotificationRegistered = notifications.contains(library.id)
                                )
                            } else {
                                origin
                            }
                        }
                    )
                }
            }
        }
    }

    private fun toggleNotification(isNotificationRegistered: Boolean, library: LibraryShort) {
        val book = uiState.value.book ?: return
        val target = BookNotificationTarget(
            libraryId = library.id,
            libraryName = library.name,
            isbn = isbn13,
            bookTitle = book.title,
        )
        viewModelScope.launch {
            runCatching {
                if (isNotificationRegistered) {
                    unregisterNotificationForBookStatusUseCase(target)
                } else {
                    registerNotificationForBookStatusUseCase(target)
                }
            }.onSuccess {
                _uiState.update {
                    it.copy(
                        status = it.status.map { status ->
                            if (status.library.id == library.id) {
                                status.copy(isNotificationRegistered = !isNotificationRegistered)
                            } else {
                                status
                            }
                        }
                    )
                }
            }
        }
    }
}
