package com.app.complaintmanagementsystem.application.usecase

import com.app.complaintmanagementsystem.domain.ComplaintRepository
import com.app.complaintmanagementsystem.domain.Complaint
import com.app.complaintmanagementsystem.domain.Country
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FindComplaintsUseCaseTest @Autowired constructor(
    private val findComplaintsUseCase: FindComplaintsUseCase,
    private val complaintRepository: ComplaintRepository,
) {

    @Test
    @Transactional
    fun `should return paginated complaints with correct size and cursor behavior`() {
        // given
        val complaints = List(15) { complaintRepository.save(createRandomComplaint()) }
            .sortedByDescending { it.id } // assuming cursor-based pagination by descending id

        // when - fetch first page
        val firstPage = findComplaintsUseCase(
            FindComplaintsUseCase.Input(
                lastId = null,
                limit = 10
            )
        )

        // then
        firstPage.shouldBeTypeOf<FindComplaintsUseCase.Output.Success>()
        firstPage.complaints.size shouldBe 10

        // when - fetch second page using last ID from first page
        val lastIdFromFirstPage = firstPage.complaints.last().id

        val secondPage = findComplaintsUseCase(
            FindComplaintsUseCase.Input(
                lastId = lastIdFromFirstPage,
                limit = 10
            )
        )

        // then
        secondPage.shouldBeTypeOf<FindComplaintsUseCase.Output.Success>()
        secondPage.complaints.size shouldBe 5
    }

    private fun createRandomComplaint(): Complaint =
        Complaint(
            productId = UUID.randomUUID(),
            reporter = UUID.randomUUID(),
            _content = UUID.randomUUID().toString(),
            country = Country.UNKNOWN
        )
}