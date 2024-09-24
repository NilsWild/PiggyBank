package de.rwth.swc.piggybank.transfers.config

import org.springframework.cloud.client.DefaultServiceInstance
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import reactor.core.publisher.Flux


class AccountTwinClientConfiguration {

    @Bean
    @Primary
    fun serviceInstanceListSupplier(): ServiceInstanceListSupplier {
        return object: ServiceInstanceListSupplier {
            override fun get(): Flux<List<ServiceInstance>> {
                return Flux.just(
                    listOf(
                        DefaultServiceInstance(
                            "accounttwin1",
                            "accounttwin",
                            "localhost",
                            8091,
                            false
                        )
                    )
                )
            }

            override fun getServiceId(): String {
                return "piggybank-accounttwin-spring-rest"
            }

        }
    }

}
