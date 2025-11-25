package com.oreocube.booksearch.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.oreocube.booksearch.domain.model.param.BookDetailParam
import com.oreocube.booksearch.domain.repository.HistoryRepository
import com.oreocube.booksearch.domain.repository.LibraryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope

@HiltWorker
class UpdateRecentHistoryWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val historyRepository: HistoryRepository,
    private val libraryRepository: LibraryRepository,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val books = historyRepository.getAll().filter { it.imageUrl.isBlank() }
            val failures = mutableListOf<String>()
            supervisorScope {
                books.map { book ->
                    async {
                        runCatching {
                            libraryRepository.getBookDetail(BookDetailParam(book.isbn))
                        }.onSuccess { detail ->
                            historyRepository.add(
                                book.copy(
                                    imageUrl = detail.imageUrl,
                                    authors = detail.authors
                                )
                            )
                        }.onFailure {
                            failures.add(book.isbn)
                        }
                    }
                }.awaitAll()
            }

            if (failures.isEmpty()) {
                Result.success()
            } else {
                Result.retry()
            }
        } catch (_: Throwable) {
            Result.retry()
        }
    }
}
