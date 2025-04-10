package com.app.complaintmanagementsystem.domain

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(
    name = "complaint",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["productId", "reporter"])
    ]
)
class Complaint(
    @Id
    val id: UUID = UUID.randomUUID(),
    val productId: UUID,
    private var _content: String,
    val reporter: UUID,
    val country: Country,
    val createdAt: Instant? = Instant.now()
) {
    @Version
    private val version: Long = 1
    private var _counter: Long = 1
    @Transient
    private var reportedAgain: Boolean = false

    fun reportAgain() {
        if (reportedAgain) return
        reportedAgain = true
        _counter++
    }

    fun updateContent(content: String) {
        _content = content
    }

    val content: String get() = _content
    val counter: Long get() = _counter
}
