package com.app.complaintmanagementsystem.domain

import java.util.UUID

interface ComplaintRepository {
    fun findById(id: UUID): Complaint?
    fun findByProductIdAndReporter(productId: UUID, reporter: UUID): Complaint?
    fun save(complaint: Complaint): Complaint
    fun findNextPage(afterId: UUID?, limit: Int): List<Complaint>
}