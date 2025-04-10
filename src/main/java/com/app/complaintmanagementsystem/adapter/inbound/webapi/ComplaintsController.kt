package com.app.complaintmanagementsystem.adapter.inbound.webapi

import com.app.complaintmanagementsystem.adapter.inbound.webapi.dto.*
import com.app.complaintmanagementsystem.application.usecase.FindComplaintsUseCase
import com.app.complaintmanagementsystem.application.usecase.ReportComplaintUseCase
import com.app.complaintmanagementsystem.application.usecase.UpdateComplaintUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ComplaintsController(
    private val reportComplaintUseCase: ReportComplaintUseCase,
    private val findComplaintsUseCase: FindComplaintsUseCase,
    private val updateComplaintUseCase: UpdateComplaintUseCase
): ComplaintsRestInterface {

    override fun reportComplaint(newComplaintRequestDto: NewComplaintRequestDto): ResponseEntity<ComplaintResponseDto> {
        return when (val output = reportComplaintUseCase(newComplaintRequestDto.toReportComplaintInput())) {
            is ReportComplaintUseCase.Output.Success -> ResponseEntity.status(HttpStatus.CREATED).body(
                output.complaint.toComplaintResponseDto()
            )
            is ReportComplaintUseCase.Output.PartiallyCreated -> ResponseEntity.status(HttpStatus.ACCEPTED).body(
                output.complaint.toComplaintResponseDto()
            )
        }
    }

    override fun findComplaints(lastId: UUID?, limit: Int): ResponseEntity<ComplaintListResponseDto> {
        return ResponseEntity.status(HttpStatus.OK).body(
            ComplaintListResponseDto(
                findComplaintsUseCase(FindComplaintsUseCase.Input(lastId, limit)).complaints.map { it.toComplaintResponseDto() }
            )
        )
    }

    override fun updateComplaint(
        complaintId: UUID,
        editComplaintRequestDto: EditComplaintRequestDto
    ): ResponseEntity<ComplaintResponseDto> {
        return when(val output = updateComplaintUseCase(UpdateComplaintUseCase.Input(
            complaintId = complaintId,
            content = editComplaintRequestDto.content
        ))) {
            is UpdateComplaintUseCase.Output.Success -> ResponseEntity.status(HttpStatus.OK).body(output.complaint.toComplaintResponseDto())
            is UpdateComplaintUseCase.Output.NotFound -> ResponseEntity.notFound().build()
        }
    }
}