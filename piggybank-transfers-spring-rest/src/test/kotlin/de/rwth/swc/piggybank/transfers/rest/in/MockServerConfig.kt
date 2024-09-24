package de.rwth.swc.piggybank.transfers.rest.`in`

import org.mockserver.integration.ClientAndServer
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

@TestConfiguration
class MockServerConfig {

    @Bean(destroyMethod = "stop")
    fun mockServerClient(): ClientAndServer {
        return ClientAndServer.startClientAndServer()
    }

    @Bean
    @Primary
    fun testServiceInstanceListSupplier(
        mockServerClient: ClientAndServer
    ): ServiceInstanceListSupplier {
        return TestServiceInstanceListSupplier(mockServerClient)
    }
}