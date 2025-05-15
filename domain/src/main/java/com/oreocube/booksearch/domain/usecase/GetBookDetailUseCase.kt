package com.oreocube.booksearch.domain.usecase

import com.oreocube.booksearch.domain.model.BookDetail
import com.oreocube.booksearch.domain.model.param.BookDetailParam
import com.oreocube.booksearch.domain.repository.LibraryRepository
import javax.inject.Inject

class GetBookDetailUseCase @Inject constructor(
    private val libraryRepository: LibraryRepository,
) {
    suspend operator fun invoke(param: BookDetailParam): BookDetail {
        return libraryRepository.getBookDetail(param)
    }
}
