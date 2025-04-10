package com.app.complaintmanagementsystem.application.usecase

import com.app.complaintmanagementsystem.domain.Complaint
import com.app.complaintmanagementsystem.domain.ComplaintService
import com.app.complaintmanagementsystem.domain.exceptions.ComplaintNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class UpdateComplaintUseCase(
    private val complaintService: ComplaintService,
) {

    data class Input(
        val complaintId: UUID,
        val content: String,
    )

    sealed class Output {
        data class Success(val complaint: Complaint) : Output()
        data class NotFound(val complaintId: UUID) : Output()
    }

    operator fun invoke(input: Input): Output = runCatching {
        val complaint = complaintService.updateContent(
            complaintId = input.complaintId,
            content = input.content,
        )
        Output.Success(complaint)
    }.getOrElse { ex ->
        when (ex) {
            is ComplaintNotFoundException -> Output.NotFound(input.complaintId)
            else -> throw ex
        }
    }

}