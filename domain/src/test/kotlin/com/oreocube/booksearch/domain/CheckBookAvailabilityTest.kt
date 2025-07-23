package com.oreocube.booksearch.domain

import com.oreocube.booksearch.domain.model.BookAvailability
import com.oreocube.booksearch.domain.model.LibraryShort
import com.oreocube.booksearch.domain.model.param.BookAvailabilityCheckParam
import com.oreocube.booksearch.domain.repository.LibraryRepository
import com.oreocube.booksearch.domain.usecase.CheckBookAvailabilityUseCase
import io.kotest.inspectors.forAll
import io.kotest.matchers.collections.shouldExistInOrder
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class CheckBookAvailabilityTest {
    private val libraryRepository = mockk<LibraryRepository>()
    private val useCase = CheckBookAvailabilityUseCase(libraryRepository)

    private val isbn = "9781234567890"
    private val library1 = LibraryShort("lib1", "서울도서관")
    private val library2 = LibraryShort("lib2", "부산도서관")
    private val library3 = LibraryShort("lib3", "대전도서관")
    private val libraries = listOf(library1, library2, library3)

    @Test
    fun `모든 도서관 상태 조회가 성공하면 모든 결과가 성공으로 반환된다`() = runTest {
        // Given
        val availability = BookAvailability(hasBook = true, loanAvailable = true)

        coEvery {
            libraryRepository.checkBookAvailability(any())
        } returns availability

        // When
        val result = useCase(isbn, libraries)

        // Then
        result.size shouldBe 3
        result.forAll {
            it.availability.shouldBeSuccess()
            it.availability.getOrNull() shouldBe availability
        }
    }

    @Test
    fun `일부 도서관 상태 조회 실패 시에도 나머지 도서관 데이터는 정상적으로 반환된다`() = runTest {
        // Given
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
        result.size shouldBe 3
        result[0].availability.shouldBeSuccess()
        result[1].availability.shouldBeFailure()
        result[2].availability.shouldBeSuccess()
    }

    @Test
    fun `모든 도서관 상태 조회가 실패해도 리스트는 반환된다`() = runTest {
        coEvery {
            libraryRepository.checkBookAvailability(any())
        } throws RuntimeException("조회 실패")

        val result = useCase(isbn, libraries)
        // Then
        result.size shouldBe 3
        result.forAll {
            it.availability.shouldBeFailure()
        }
    }

    @Test
    fun `빈 리스트를 입력하면 빈 결과를 반환한다`() = runTest {
        val libraries = emptyList<LibraryShort>()
        val result = useCase(isbn, libraries)

        result shouldBe emptyList()
    }

    @Test
    fun `도서관 입력 순서와 결과 순서는 동일하다`() = runTest {
        val availability1 = BookAvailability(hasBook = true, loanAvailable = true)
        val availability2 = BookAvailability(hasBook = false, loanAvailable = false)
        val availability3 = BookAvailability(hasBook = true, loanAvailable = false)

        coEvery {
            libraryRepository.checkBookAvailability(BookAvailabilityCheckParam(isbn, library1.id))
        } returns availability1

        coEvery {
            libraryRepository.checkBookAvailability(BookAvailabilityCheckParam(isbn, library2.id))
        } throws RuntimeException("조회 실패")

        coEvery {
            libraryRepository.checkBookAvailability(BookAvailabilityCheckParam(isbn, library3.id))
        } returns availability3

        val result = useCase(isbn, libraries)

        result.size shouldBe 3
        result shouldExistInOrder listOf(
            { it.library.id == library1.id && it.availability.getOrNull() == availability1 },
            { it.availability.isFailure },
            { it.library.id == library3.id && it.availability.getOrNull() == availability3 },
        )
    }
}
