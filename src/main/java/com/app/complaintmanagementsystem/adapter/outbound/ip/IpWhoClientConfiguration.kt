package com.app.complaintmanagementsystem.adapter.outbound.ip

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class IpWhoClientConfiguration(
    @Value("\${ip-who.url}") private val serviceUrl: String
) {

    @Bean
    fun ipWhoClient(): IpWhoClient {
        val webClient = WebClient.builder()
            .baseUrl(serviceUrl)
            .build()

        return HttpServiceProxyFactory
            .builderFor(WebClientAdapter.create(webClient))
            .build()
            .createClient(IpWhoClient::class.java)
    }
}