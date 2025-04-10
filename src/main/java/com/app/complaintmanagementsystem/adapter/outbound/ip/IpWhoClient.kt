package com.app.complaintmanagementsystem.adapter.outbound.ip

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import reactor.core.publisher.Mono

@HttpExchange
interface IpWhoClient {
    @GetExchange("/{ip}")
    fun getCountryForIp(@PathVariable ip: String): Mono<IpWhoResponse>
}

data class IpWhoResponse(
    val ip: String,
    val success: Boolean,
    @JsonProperty("country_code")
    val countryCode: String,
    val message: String? = null
)