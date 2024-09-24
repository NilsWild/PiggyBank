package de.rwth.swc.piggybank.transfers.rest.out

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

/**
 * Configuration class for setting up the AccountTwinClient.
 */
@Configuration
class AccountTwinClientConfiguration {

    /**
     * Creates a bean for the AccountTwinService client.
     *
     * @param loadBalancedWebClientBuilder The WebClient.Builder with load balancing capabilities.
     * @return An instance of AccountTwinService.
     */
    @Bean
    fun accountTwinClient(loadBalancedWebClientBuilder: WebClient.Builder): AccountTwinService {
        val httpServiceProxyFactory = HttpServiceProxyFactory
            .builderFor(
                WebClientAdapter.create(
                    loadBalancedWebClientBuilder
                        .baseUrl("http://piggybank-accounttwin-spring-rest")
                        .build()
                )
            ).build()
        return httpServiceProxyFactory.createClient(AccountTwinService::class.java)
    }
}