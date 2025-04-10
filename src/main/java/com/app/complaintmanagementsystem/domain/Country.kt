package com.app.complaintmanagementsystem.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.jetbrains.annotations.NotNull

@Embeddable
data class Country(
    @NotNull
    @Column(name = "country_value")
    val value: String
) {
    companion object {
        val UNKNOWN = Country("??")
    }

    init {
        require(value.length == 2) {
            "country length is limited to size 2"
        }
    }

    fun isUnknown(): Boolean = value == UNKNOWN.value

    override fun toString(): String {
        return this.value
    }
}
