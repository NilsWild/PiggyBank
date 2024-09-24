package de.rwth.swc.piggybank.transfers.config

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients
import org.springframework.context.annotation.Configuration

@Configuration
class AccountTwinLoadBalancerClient