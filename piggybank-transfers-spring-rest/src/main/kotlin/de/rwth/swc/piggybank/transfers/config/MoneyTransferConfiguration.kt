package de.rwth.swc.piggybank.transfers.config

import DefaultMoneyTransferItemService
import de.rwth.swc.piggybank.domain.transfers.spi.MoneyTransferItemChangeListener
import de.rwth.swc.piggybank.domain.transfers.spi.MoneyTransferItems
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import io.github.projectmapk.jackson.module.kogera.KotlinFeature
import io.github.projectmapk.jackson.module.kogera.KotlinModule

/**
 * Configuration class for setting up beans for the domain services and adding required configurations.
 */
@Configuration
class MoneyTransferConfiguration {

    /**
     * Creates a bean for the DefaultMoneyTransferItemService.
     *
     * @param moneyTransferItems The repository for money transfer items.
     * @param moneyTransferItemChangeListeners The list of listeners for money transfer item events.
     * @return An instance of DefaultMoneyTransferItemService.
     */
    @Bean
    fun moneyTransferItemService(
        moneyTransferItems: MoneyTransferItems,
        moneyTransferItemChangeListeners: List<MoneyTransferItemChangeListener>
    ) = DefaultMoneyTransferItemService(
        moneyTransferItems,
        moneyTransferItemChangeListeners
    )

    /**
     * Creates a bean for the KotlinModule with specific configurations.
     *
     * @return An instance of KotlinModule with SingletonSupport enabled.
     */
    @Bean
    fun kotlinModule() = KotlinModule.Builder().configure(
        KotlinFeature.SingletonSupport, true
    ).build()
}