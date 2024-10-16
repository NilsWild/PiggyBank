package de.rwth.swc.piggybank.transfers.config

import DefaultMoneyTransferItemService
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import de.rwth.swc.piggybank.domain.transfers.DefaultAccountWatchService
import de.rwth.swc.piggybank.domain.transfers.api.AccountWatchService
import de.rwth.swc.piggybank.domain.transfers.spi.AccountWatches
import de.rwth.swc.piggybank.domain.transfers.spi.MoneyTransferItemChangeListener
import de.rwth.swc.piggybank.domain.transfers.spi.MoneyTransferItems
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration class for setting up beans for the domain services and adding required configurations.
 */
@Configuration
class MoneyTransferConfiguration {

    /**
     * Creates a bean for the DefaultAccountWatchService.
     *
     * @param accountWatches The repository for account watches.
     */
    @Bean fun accountWatchService(
        accountWatches: AccountWatches
    ) = DefaultAccountWatchService(accountWatches)

    /**
     * Creates a bean for the DefaultMoneyTransferItemService.
     *
     * @param moneyTransferItems The repository for money transfer items.
     * @param accountWatchService The service for account watches.
     * @param moneyTransferItemChangeListeners The list of listeners for money transfer item events.
     * @return An instance of DefaultMoneyTransferItemService.
     */
    @Bean
    fun moneyTransferItemService(
        moneyTransferItems: MoneyTransferItems,
        accountWatchService: AccountWatchService,
        moneyTransferItemChangeListeners: List<MoneyTransferItemChangeListener>
    ) = DefaultMoneyTransferItemService(
        moneyTransferItems,
        accountWatchService,
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