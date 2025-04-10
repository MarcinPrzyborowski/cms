package com.app.complaintmanagementsystem.application.usecase

import com.app.complaintmanagementsystem.domain.Complaint
import com.app.complaintmanagementsystem.domain.ComplaintService
import com.app.complaintmanagementsystem.domain.CountryProvider
import org.springframework.stereotype.Service
import java.util.*

@Service
class ReportComplaintUseCase(
    private val complaintService: ComplaintService,
    private val countryProvider: CountryProvider
) {

    data class Input(
        val productId: UUID,
        val reporter: UUID,
        val content: String,
        val ip: String
    )

    sealed class Output {
        data class Success(val complaint: Complaint) : Output()
        data class PartiallyCreated(val complaint: Complaint) : Output()
    }

    operator fun invoke(input: Input): Output {
        val country = countryProvider.provide(input.ip)

        val complaint = complaintService.reportComplain(
            productId = input.productId,
            content = input.content,
            reporter = input.reporter,
            country = country
        )

        if (country.isUnknown()) {
            return Output.PartiallyCreated(complaint)
        }

        return Output.Success(complaint)
    }

}