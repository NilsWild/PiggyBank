package de.rwth.swc.piggybank.transfers.rest.out

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory


@Configuration
class AccountTwinClient {

    @Bean
    fun accountTwinClient(loadBalancedWebClientBuilder: WebClient.Builder): AccountTwinService {
        val httpServiceProxyFactory = HttpServiceProxyFactory
            .builder(
                WebClientAdapter.forClient(
                    loadBalancedWebClientBuilder
                        .baseUrl("http://piggybank-accounttwin-spring-rest")
                        .build()
                )
            ).build()
        return httpServiceProxyFactory.createClient(AccountTwinService::class.java)
    }
}