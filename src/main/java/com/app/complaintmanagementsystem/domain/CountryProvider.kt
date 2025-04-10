package com.app.complaintmanagementsystem.domain

interface CountryProvider {

    fun provide(ip: String): Country

}