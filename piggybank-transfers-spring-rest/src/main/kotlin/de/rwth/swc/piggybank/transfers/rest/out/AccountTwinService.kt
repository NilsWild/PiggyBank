package de.rwth.swc.piggybank.transfers.rest.out

import de.rwth.swc.piggybank.domain.shared.valueobject.AccountIdentifier
import de.rwth.swc.piggybank.domain.shared.valueobject.AccountType
import de.rwth.swc.piggybank.domain.shared.valueobject.Money
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.PostExchange
import reactor.core.publisher.Flux

/**
 * Service interface for interacting with the AccountTwin service.
 */
interface AccountTwinService {

    /**
     * Adds money to a specified account.
     *
     * @param accountType The type of the account.
     * @param accountIdentifier The identifier of the account.
     * @param money The amount of money to add.
     * @return A Flux that completes when the operation is done.
     */
    @PostExchange("/api/accounts/{accountType}/{accountIdentifier}/addMoney")
    fun addMoney(
        @PathVariable accountType: AccountType,
        @PathVariable accountIdentifier: AccountIdentifier,
        @RequestBody money: Money
    ): Flux<Void>
}