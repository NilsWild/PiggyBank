package de.rwth.swc.piggybank.transfers.config

import org.springframework.cloud.client.DefaultServiceInstance
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux

/**
 * Configuration class for load balancing the AccountTwinClient.
 */
@Configuration
class AccountTwinClientLoadBalancingConfiguration {

    /**
     * Provides a list of service instances for the AccountTwinClient.
     *
     * @return A ServiceInstanceListSupplier that supplies a list of service instances.
     */
    @Bean
    fun serviceInstanceListSupplier(): ServiceInstanceListSupplier {
        return object : ServiceInstanceListSupplier {
            /**
             * Returns a Flux containing a list of service instances.
             *
             * @return A Flux containing a list of ServiceInstance objects.
             */
            override fun get(): Flux<List<ServiceInstance>> {
                return Flux.just(
                    listOf(
                        DefaultServiceInstance(
                            "piggybank-accounttwin-spring-rest1", // Instance ID
                            "piggybank-accounttwin-spring-rest",  // Service ID
                            "localhost",                          // Host
                            8091,                                 // Port
                            false                                 // Secure
                        )
                    )
                )
            }

            /**
             * Returns the service ID for the AccountTwinClient.
             *
             * @return The service ID as a String.
             */
            override fun getServiceId(): String {
                return "piggybank-accounttwin-spring-rest"
            }
        }
    }
}