package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.model.BookDetail
import com.oreocube.booksearch.domain.model.param.BookDetailParam
import com.oreocube.booksearch.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBookDetailUseCase @Inject constructor(
    private val libraryRepository: LibraryRepository,
) {
    operator fun invoke(param: BookDetailParam): Flow<BookDetail> = flow {
        emit(libraryRepository.getBookDetail(param))
    }
}
