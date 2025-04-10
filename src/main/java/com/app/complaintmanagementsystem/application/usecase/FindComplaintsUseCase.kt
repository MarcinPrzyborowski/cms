package com.app.complaintmanagementsystem.application.usecase

import com.app.complaintmanagementsystem.domain.Complaint
import com.app.complaintmanagementsystem.domain.ComplaintService
import org.springframework.stereotype.Service
import java.util.*

@Service
class FindComplaintsUseCase(
    private val complaintService: ComplaintService,
) {

    data class Input(
        val lastId: UUID?,
        val limit: Int
    )

    sealed class Output {
        data class Success(val complaints: List<Complaint>) : Output()
    }

    operator fun invoke(input: Input) = Output.Success(complaintService.findComplaints(input.lastId, input.limit))
}