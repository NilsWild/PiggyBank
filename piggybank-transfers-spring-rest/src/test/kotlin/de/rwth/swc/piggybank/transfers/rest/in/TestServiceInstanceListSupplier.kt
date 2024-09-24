package de.rwth.swc.piggybank.transfers.rest.`in`

import org.mockserver.client.MockServerClient
import org.springframework.cloud.client.DefaultServiceInstance
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier
import reactor.core.publisher.Flux

class TestServiceInstanceListSupplier(private val mockServerClient: MockServerClient) : ServiceInstanceListSupplier {
    override fun getServiceId(): String {
        return "piggybank-accounttwin-spring-rest"
    }

    override fun get(): Flux<List<ServiceInstance>> {
        val instance: ServiceInstance = DefaultServiceInstance(
            "piggybank-accounttwin-spring-rest1",
            "piggybank-accounttwin-spring-rest",
            mockServerClient.remoteAddress().hostName,
            mockServerClient.remoteAddress().port,
            false
        )
        return Flux.just(listOf(instance))
    }
}