package com.app.complaintmanagementsystem.adapter.inbound.webapi

import com.app.complaintmanagementsystem.adapter.inbound.webapi.dto.ComplaintResponseDto
import com.app.complaintmanagementsystem.adapter.inbound.webapi.dto.NewComplaintRequestDto
import com.app.complaintmanagementsystem.application.usecase.ReportComplaintUseCase
import com.app.complaintmanagementsystem.domain.Complaint

fun Complaint.toComplaintResponseDto() = ComplaintResponseDto(
    complaintId = this.id,
    productId = this.productId,
    reporter = this.reporter,
    content = this.content,
    country = this.country.value,
    counter = this.counter
)

fun NewComplaintRequestDto.toReportComplaintInput() = ReportComplaintUseCase.Input(
    productId = this.productId,
    reporter = this.reporter,
    content = this.content,
    ip = this.ip
)
