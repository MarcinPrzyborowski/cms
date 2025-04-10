package com.app.complaintmanagementsystem.adapter.outbound.ip

import com.app.complaintmanagementsystem.domain.Country
import com.app.complaintmanagementsystem.domain.CountryProvider
import org.springframework.stereotype.Service

@Service
class IpWhoCountryProvider(private val ipWhoClient: IpWhoClient): CountryProvider {

    override fun provide(ip: String): Country {
        return try {
            val response = ipWhoClient.getCountryForIp(ip).blockOptional()
            response
                .map { it.toCountry() }
                .orElseGet { Country.UNKNOWN }
        } catch (ex: Exception) {
            println("Error during resolving IP=$ip: ${ex.message}")
            Country.UNKNOWN
        }
    }

    private fun IpWhoResponse.toCountry() = Country(this.countryCode)

}