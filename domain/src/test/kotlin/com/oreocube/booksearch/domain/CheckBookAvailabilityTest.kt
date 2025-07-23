package com.oreocube.booksearch.domain

import com.oreocube.booksearch.domain.model.BookAvailability
import com.oreocube.booksearch.domain.model.LibraryShort
import com.oreocube.booksearch.domain.model.param.BookAvailabilityCheckParam
import com.oreocube.booksearch.domain.repository.LibraryRepository
import com.oreocube.booksearch.domain.usecase.CheckBookAvailabilityUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CheckBookAvailabilityTest {
    private val libraryRepository = mockk<LibraryRepository>()
    private val useCase = CheckBookAvailabilityUseCase(libraryRepository)

    @Test
    fun `모든 도서관 상태 조회가 성공하면 모든 결과가 성공으로 반환된다`() = runTest {
        // Given
        val isbn = "9781234567890"
        val library1 = LibraryShort("lib1", "서울도서관")
        val library2 = LibraryShort("lib2", "부산도서관")
        val library3 = LibraryShort("lib3", "대전도서관")
        val libraries = listOf(library1, library2, library3)

        val availability = BookAvailability(hasBook = true, loanAvailable = true)

        libraries.forEach {
            coEvery {
                libraryRepository.checkBookAvailability(
                    BookAvailabilityCheckParam(isbn, it.id)
                )
            } returns availability
        }

        // When
        val result = useCase(isbn, libraries)

        // Then
        assertEquals(3, result.size)
        result.forEach {
            assertTrue(it.availability.isSuccess)
            assertEquals(availability, it.availability.getOrNull())
        }
    }

    @Test
    fun `일부 도서관 상태 조회 실패 시에도 나머지 도서관 데이터는 정상적으로 반환된다`() = runTest {
        // Given
        val isbn = "9781234567890"
        val library1 = LibraryShort("lib1", "서울도서관")
        val library2 = LibraryShort("lib2", "부산도서관")
        val library3 = LibraryShort("lib3", "대전도서관")
        val libraries = listOf(library1, library2, library3)

        coEvery {
            libraryRepository.checkBookAvailability(BookAvailabilityCheckParam(isbn, library1.id))
        } returns BookAvailability(hasBook = true, loanAvailable = true)

        coEvery {
            libraryRepository.checkBookAvailability(BookAvailabilityCheckParam(isbn, library2.id))
        } throws RuntimeException("조회 실패")

        coEvery {
            libraryRepository.checkBookAvailability(BookAvailabilityCheckParam(isbn, library3.id))
        } returns BookAvailability(hasBook = false, loanAvailable = false)

        // When
        val result = useCase(isbn, libraries)

        // Then
        assertEquals(3, result.size)
        result.forEach {
            if (it.library.id == library2.id) {
                assertTrue(it.availability.isFailure)
            } else {
                assertTrue(it.availability.isSuccess)
            }
        }
    }
}
