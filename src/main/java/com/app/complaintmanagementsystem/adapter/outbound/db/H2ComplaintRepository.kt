package com.app.complaintmanagementsystem.adapter.outbound.db

import com.app.complaintmanagementsystem.domain.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class H2ComplaintRepository(
    private val h2JpaComplaintRepository: H2JpaComplaintRepository,
): ComplaintRepository {

    override fun findById(id: UUID): Complaint? {
        return h2JpaComplaintRepository.findByIdOrNull(id)
    }

    override fun findByProductIdAndReporter(productId: UUID, reporter: UUID): Complaint? {
        return h2JpaComplaintRepository.findByProductIdAndReporter(productId, reporter)
    }

    override fun save(complaint: Complaint): Complaint {
        return h2JpaComplaintRepository.save(complaint)
    }

    override fun findNextPage(afterId: UUID?, limit: Int): List<Complaint> {
        return h2JpaComplaintRepository.findNextPage(afterId, limit)
    }
}