package com.app.complaintmanagementsystem.domain

import com.app.complaintmanagementsystem.domain.exceptions.ComplaintNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ComplaintService(
    private val complaintRepository: ComplaintRepository,
) {

    @Transactional
    fun reportComplain(productId: UUID, content: String, reporter: UUID, country: Country): Complaint {
        val complaint = complaintRepository.findByProductIdAndReporter(productId, reporter)

        if (complaint != null) {
            complaint.reportAgain()
            this.complaintRepository.save(complaint)
            return complaint
        }

        return complaintRepository.save(Complaint(
            productId = productId,
            _content = content,
            reporter = reporter,
            country = country
        ))
    }

    @Transactional
    fun updateContent(complaintId: UUID, content: String): Complaint {
        val complaint = complaintRepository.findById(complaintId) ?: throw ComplaintNotFoundException("complaint not found")
        complaint.updateContent(content)
        return complaintRepository.save(complaint)
    }

    fun findComplaints(lastId: UUID?, limit: Int): List<Complaint> {
        return complaintRepository.findNextPage(lastId, limit)
    }

}