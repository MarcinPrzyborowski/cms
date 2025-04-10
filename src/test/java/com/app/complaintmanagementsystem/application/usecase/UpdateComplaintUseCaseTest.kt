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
class UpdateComplaintUseCaseTest @Autowired constructor(
    private val updateComplaintUseCase: UpdateComplaintUseCase,
    private val complaintRepository: ComplaintRepository,
) {

    @Test
    @Transactional
    fun `should update existing complaint content and increment counter`() {
        // given
        val productId = UUID.randomUUID()
        val reporter = UUID.randomUUID()
        val complaint = complaintRepository.save(
            Complaint(
                productId = productId,
                reporter = reporter,
                _content = "test",
                country = Country.UNKNOWN
            )
        )

        // when
        val output = updateComplaintUseCase(
            UpdateComplaintUseCase.Input(
                complaintId = complaint.id,
                content = "abc"
            )
        )

        // then
        output.shouldBeTypeOf<UpdateComplaintUseCase.Output.Success>()
        output.complaint.content shouldBe "abc"
        output.complaint.counter shouldBe 1
    }

    @Test
    @Transactional
    fun `should return NotFound when complaint does not exist`() {
        // given
        val nonExistingComplaintId = UUID.randomUUID()

        // when
        val output = updateComplaintUseCase(
            UpdateComplaintUseCase.Input(
                complaintId = nonExistingComplaintId,
                content = "abc"
            )
        )

        // then
        output.shouldBeTypeOf<UpdateComplaintUseCase.Output.NotFound>()
    }
}
