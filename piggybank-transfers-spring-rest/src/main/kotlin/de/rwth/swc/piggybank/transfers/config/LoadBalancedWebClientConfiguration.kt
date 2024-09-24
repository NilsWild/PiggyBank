package de.rwth.swc.piggybank.transfers.config

import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

/**
 * Configuration class for setting up load balancing for WebClients.
 */
@Configuration
class LoadBalancerConfiguration {

    /**
     * Creates a WebClient.Builder bean with load balancing capabilities.
     *
     * @return A WebClient.Builder instance with load balancing enabled.
     */
    @LoadBalanced
    @Bean
    fun webClientBuilder(): WebClient.Builder {
        return WebClient.builder()
    }
}