package com.app.complaintmanagementsystem.domain.exceptions

sealed class DomainException(message: String) : RuntimeException(message)