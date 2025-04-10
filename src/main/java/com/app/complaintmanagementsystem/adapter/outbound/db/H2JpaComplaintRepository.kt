package com.app.complaintmanagementsystem.adapter.outbound.db

import com.app.complaintmanagementsystem.domain.Complaint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface H2JpaComplaintRepository: JpaRepository<Complaint, UUID> {
    fun findByProductIdAndReporter(productId: UUID, reporter: UUID): Complaint?

    @Query(
        nativeQuery = true,
        value = """
        SELECT * FROM complaint 
        WHERE (:afterId IS NULL OR id > :afterId)
        ORDER BY id ASC
        LIMIT :limit
    """
    )
    fun findNextPage(
        @Param("afterId") afterId: UUID?,
        @Param("limit") limit: Int
    ): List<Complaint>
}