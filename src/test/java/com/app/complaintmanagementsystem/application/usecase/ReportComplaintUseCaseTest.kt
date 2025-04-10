package com.app.complaintmanagementsystem.application.usecase

import com.app.complaintmanagementsystem.domain.ComplaintRepository
import com.app.complaintmanagementsystem.domain.Country
import com.app.complaintmanagementsystem.domain.CountryProvider
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReportComplaintUseCaseTest @Autowired constructor(
    private val reportComplaintUseCase: ReportComplaintUseCase,
    private val complaintRepository: ComplaintRepository,
    @MockitoBean private val countryProvider: CountryProvider
) {

    @Test
    @Transactional
    fun `should append to existing complaint and increment counter when duplicate report is made`() {
        // given
        val productId = UUID.randomUUID()
        val reporter = UUID.randomUUID()
        val ipAddress = "8.8.8.8"

        whenever(countryProvider.provide(ipAddress)).thenReturn(Country("US"))

        reportComplaintUseCase.invoke(
            ReportComplaintUseCase.Input(
                productId = productId,
                reporter = reporter,
                content = "First issue",
                ip = ipAddress
            )
        )

        // when
        val output = reportComplaintUseCase.invoke(
            ReportComplaintUseCase.Input(
                productId = productId,
                reporter = reporter,
                content = "Second issue",
                ip = ipAddress
            )
        )

        // then
        output.shouldBeTypeOf<ReportComplaintUseCase.Output.Success>()
        val updatedComplaint = complaintRepository.findByProductIdAndReporter(productId, reporter)

        updatedComplaint.shouldNotBeNull()
        updatedComplaint.productId shouldBe productId
        updatedComplaint.reporter shouldBe reporter
        updatedComplaint.content shouldBe "First issue" // zakładam, że content nie jest nadpisywany
        updatedComplaint.country.value shouldBe "US"
        updatedComplaint.counter shouldBe 2
    }

    @Test
    @Transactional
    fun `should create complaint with UNKNOWN country and return partially created when country not resolved`() {
        // given
        val productId = UUID.randomUUID()
        val reporter = UUID.randomUUID()
        val ipAddress = "8.8.8.8"

        whenever(countryProvider.provide(ipAddress)).thenReturn(Country.UNKNOWN)

        // when
        val output = reportComplaintUseCase.invoke(
            ReportComplaintUseCase.Input(
                productId = productId,
                reporter = reporter,
                content = "Some issue",
                ip = ipAddress
            )
        )

        // then
        output.shouldBeTypeOf<ReportComplaintUseCase.Output.PartiallyCreated>()
        val complaint = complaintRepository.findByProductIdAndReporter(productId, reporter)

        complaint.shouldNotBeNull()
        complaint.productId shouldBe productId
        complaint.reporter shouldBe reporter
        complaint.content shouldBe "Some issue"
        complaint.country.value shouldBe "??"
    }
}