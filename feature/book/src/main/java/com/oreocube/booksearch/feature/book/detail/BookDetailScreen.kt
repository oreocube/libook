package com.oreocube.booksearch.feature.book.detail

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil3.compose.AsyncImage
import com.oreocube.booksearch.core.ui.R
import com.oreocube.booksearch.core.ui.component.LiBookTopBar
import com.oreocube.booksearch.core.ui.theme.Brown20
import com.oreocube.booksearch.core.ui.theme.Gray10
import com.oreocube.booksearch.core.ui.theme.Gray20
import com.oreocube.booksearch.core.ui.theme.Gray90
import com.oreocube.booksearch.core.ui.theme.LiBookPreviewTheme
import com.oreocube.booksearch.domain.model.BookDetail
import com.oreocube.booksearch.domain.model.LibraryShort
import com.oreocube.booksearch.feature.book.model.BookStatusUiState
import com.oreocube.booksearch.feature.book.model.LibraryBookStatusUiState
import com.oreocube.booksearch.feature.book.model.RecommendedBookUiState

@Composable
fun BookDetailRoute(
    onBackClick: () -> Unit,
    onAddLibraryClick: () -> Unit,
    onBookItemClick: (String) -> Unit,
    onShowSnackbar: (String) -> Unit,
    viewModel: BookDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var hasNotificationPermission by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            else true
        )
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasNotificationPermission = isGranted
    }

    BookDetailScreen(
        modifier = Modifier.fillMaxSize(),
        uiState = uiState,
        onBackClick = onBackClick,
        onRetryClick = { viewModel.submitIntent(BookDetailIntent.RefreshBookAvailability(it)) },
        onAlarmClick = { isRegistered, library ->
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU
                || hasNotificationPermission
            ) {
                viewModel.submitIntent(BookDetailIntent.ToggleNotification(isRegistered, library))
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        },
        onHeartClick = { viewModel.submitIntent(BookDetailIntent.ToggleHeart) },
        onAddLibraryClick = onAddLibraryClick,
        onBookItemClick = onBookItemClick,
    )

    LaunchedEffect(Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.submitIntent(BookDetailIntent.EnterScreen)
        }
        viewModel.sideEffect.collect { event ->
            when (event) {
                BookDetailSideEffect.NavigateToAddLibrary -> onAddLibraryClick()
                is BookDetailSideEffect.NavigateToBookDetail -> onBookItemClick(event.isbn)
                is BookDetailSideEffect.Error -> onShowSnackbar(event.message)
            }
        }
    }
}

@Composable
fun BookDetailScreen(
    modifier: Modifier = Modifier,
    uiState: BookDetailUiState,
    onBackClick: () -> Unit,
    onRetryClick: (LibraryShort) -> Unit,
    onAlarmClick: (Boolean, LibraryShort) -> Unit,
    onHeartClick: () -> Unit,
    onAddLibraryClick: () -> Unit,
    onBookItemClick: (String) -> Unit,
) {
    val heartIcon = if (uiState.isFavorite) {
        Icons.Filled.Favorite
    } else {
        Icons.Outlined.FavoriteBorder
    }

    Column(modifier = modifier) {
        LiBookTopBar(
            menuIcon = {
                IconButton(onClick = onHeartClick) {
                    Icon(
                        painter = rememberVectorPainter(heartIcon),
                        contentDescription = stringResource(R.string.menu_back),
                    )
                }
            },
            onNavigationIconClick = onBackClick,
        )
        when {
            uiState.isLoading -> {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }

            uiState.book != null -> {
                val scrollState = rememberScrollState()

                Column(
                    modifier = modifier
                        .verticalScroll(scrollState)
                        .background(color = Color.White)
                ) {
                    BookDetailContent(
                        modifier = Modifier.fillMaxWidth(),
                        book = uiState.book,
                    )
                    HorizontalDivider()
                    LibraryStatusForBook(
                        modifier = Modifier.fillMaxWidth(),
                        status = uiState.status,
                        onRetryClick = onRetryClick,
                        onAddLibraryClick = onAddLibraryClick,
                        onAlarmClick = onAlarmClick,
                    )
                    if (uiState.recommendBooks.isNotEmpty()) {
                        HorizontalDivider()
                        RecommendedBookSection(
                            books = uiState.recommendBooks,
                            onBookItemClick = onBookItemClick,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BookDetailContent(
    modifier: Modifier = Modifier,
    book: BookDetail
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                model = book.imageUrl,
                contentDescription = "book image",
            )
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 8.dp),
            ) {
                Text(
                    text = book.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = book.authors,
                    fontSize = 14.sp,
                    color = Gray20
                )
                Text(
                    text = "${book.publisher} · ${book.publicationYear}",
                    fontSize = 14.sp,
                    color = Gray20,
                )
            }
        }
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "책 소개",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = Gray90)
                .padding(16.dp),
        ) {
            Text(
                text = book.description,
                fontSize = 16.sp,
                color = Gray10
            )
        }
    }
}

@Composable
private fun LibraryStatusForBook(
    modifier: Modifier = Modifier,
    status: List<LibraryBookStatusUiState>,
    onAlarmClick: (Boolean, LibraryShort) -> Unit,
    onRetryClick: (LibraryShort) -> Unit,
    onAddLibraryClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        Text(
            text = "관심 도서관 비치 현황",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
        if (status.isEmpty()) {
            EmptyFavoriteLibraryContent(
                onAddLibraryClick = onAddLibraryClick
            )
        } else {
            status.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(color = Gray90)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = item.library.name, fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Row(
                        modifier = Modifier.padding(start = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (item.status == null) {
                            CircularProgressIndicator()
                        } else {
                            StatusLabel(
                                text = item.status.text,
                                textColor = item.status.textColor,
                                containerColor = item.status.containerColor,
                            )
                        }
                        if (item.status == BookStatusUiState.ON_LOAN) {
                            IconButton(
                                modifier = Modifier.size(20.dp),
                                onClick = {
                                    onAlarmClick(
                                        item.isNotificationRegistered,
                                        item.library,
                                    )
                                }
                            ) {
                                Icon(
                                    imageVector = if (item.isNotificationRegistered) Icons.Filled.Notifications else Icons.Outlined.Notifications,
                                    contentDescription = "notification",
                                    tint = Color.Black,
                                )
                            }
                        }
                        if (item.status == BookStatusUiState.ERROR) {
                            IconButton(
                                modifier = Modifier.size(20.dp),
                                onClick = { onRetryClick(item.library) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "refresh",
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyFavoriteLibraryContent(
    onAddLibraryClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.no_favorite_library_and_extra_information),
            style = MaterialTheme.typography.bodyMedium,
            color = Gray20,
            textAlign = TextAlign.Center,
        )
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .clip(shape = RoundedCornerShape(32.dp))
                .background(color = Brown20)
                .clickable(onClick = onAddLibraryClick)
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                tint = Color.White,
                painter = painterResource(R.drawable.ic_search_24),
                contentDescription = stringResource(R.string.menu_search_library)
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(R.string.menu_search_library),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
            )
        }
    }
}

@Composable
private fun StatusLabel(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    containerColor: Color,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(color = containerColor)
            .padding(vertical = 2.dp, horizontal = 8.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 12.sp,
        )
    }
}

@Composable
private fun RecommendedBookSection(
    books: List<RecommendedBookUiState>,
    onBookItemClick: (String) -> Unit,
) {
    Column {
        Text(
            modifier = Modifier.padding(top = 16.dp, start = 16.dp),
            text = "같이 볼만한 도서",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(
                items = books,
                key = { book -> book.isbn13 }
            ) { book ->
                RecommendedBookItem(book = book, onClick = onBookItemClick)
            }
        }
    }
}

@Composable
private fun RecommendedBookItem(
    book: RecommendedBookUiState,
    onClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .width(120.dp)
            .clickable { onClick(book.isbn13) }
    ) {
        AsyncImage(
            modifier = Modifier.size(width = 120.dp, height = 150.dp),
            model = book.imageUrl,
            contentDescription = "recommended book image",
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = book.title,
            fontSize = 14.sp,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun LibraryStatusForBookPreview1() {
    LibraryStatusForBook(
        status = listOf(
            LibraryBookStatusUiState(
                library = LibraryShort("1", "도서관1"),
                status = BookStatusUiState.ON_LOAN,
                isNotificationRegistered = true,
            ),
            LibraryBookStatusUiState(
                library = LibraryShort("2", "도서관2"),
                status = BookStatusUiState.NOT_AVAILABLE,
                isNotificationRegistered = false,
            ),
            LibraryBookStatusUiState(
                library = LibraryShort("3", "도서관3"),
                status = BookStatusUiState.AVAILABLE,
                isNotificationRegistered = false,
            ),
            LibraryBookStatusUiState(
                library = LibraryShort("4", "도서관4"),
                status = BookStatusUiState.ERROR,
                isNotificationRegistered = false,
            ),
        ),
        onRetryClick = {},
        onAddLibraryClick = {},
        onAlarmClick = { _, _ -> },
    )
}

@Composable
@Preview(showBackground = true)
private fun LibraryStatusForBookPreview2() {
    LibraryStatusForBook(
        status = emptyList(),
        onRetryClick = {},
        onAddLibraryClick = {},
        onAlarmClick = { _, _ -> },
    )
}

@Composable
@Preview(showBackground = true)
private fun BookDetailScreenPreview() {
    LiBookPreviewTheme {
        BookDetailScreen(
            uiState = BookDetailUiState(
                isLoading = false,
                isFavorite = false,
                book = BookDetail(
                    title = "실용주의 프로그래머 :20주년 기념판 ",
                    authors = "데이비드 토머스,정지용 옮김",
                    publisher = "인사이트",
                    publicationYear = "2022",
                    isbn13 = "9788966263363",
                    imageUrl = "https://image.aladin.co.kr/product/28878/64/cover/8966263364_1.jpg",
                    description = "실용주의 프로그래머 20주년 기념판. 데이비드 토마스와 앤드류 헌트는 소프트웨어 산업에 큰 영향을 미친 이 책의 1판을 1999년에 썼다. 고객들이 더 나은 소프트웨어를 만들고 코딩의 기쁨을 재발견하도록 돕기 위해서였다."
                ),
                status = listOf(
                    LibraryBookStatusUiState(
                        library = LibraryShort("1", "도서관1"),
                        status = BookStatusUiState.ON_LOAN,
                        isNotificationRegistered = true,
                    ),
                    LibraryBookStatusUiState(
                        library = LibraryShort("2", "도서관2"),
                        status = BookStatusUiState.NOT_AVAILABLE,
                        isNotificationRegistered = false,
                    ),
                    LibraryBookStatusUiState(
                        library = LibraryShort("3", "도서관3"),
                        status = BookStatusUiState.AVAILABLE,
                        isNotificationRegistered = false,
                    ),
                    LibraryBookStatusUiState(
                        library = LibraryShort("4", "도서관4"),
                        status = BookStatusUiState.ERROR,
                        isNotificationRegistered = false,
                    ),
                ),
                recommendBooks = listOf(
                    RecommendedBookUiState(
                        title = "실용주의 프로그래머 :20주년 기념판 ",
                        authors = "데이비드 토머스,정지용 옮김",
                        publisher = "인사이트",
                        publicationYear = "2022",
                        isbn13 = "978",
                        imageUrl = "https://image.aladin.co.kr/product/28878/64/cover/8966263364_1.jpg",
                    ),
                ),
            ),
            onBackClick = {},
            onRetryClick = {},
            onAlarmClick = { _, _ -> },
            onHeartClick = {},
            onAddLibraryClick = {},
            onBookItemClick = {},
        )
    }
}
