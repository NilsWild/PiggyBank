package de.rwth.swc.piggybank.domain.transfers.api

import de.rwth.swc.piggybank.domain.transfers.entity.MoneyTransferItem
import de.rwth.swc.piggybank.domain.shared.valueobject.Account

/**
 * Service interface for managing money transfer items.
 */
interface MoneyTransferItemService {

    /**
     * Adds a money transfer item.
     *
     * @param item The money transfer item to add.
     */
    fun add(item: MoneyTransferItem)

    /**
     * Retrieves all money transfer items.
     *
     * @return A collection of all money transfer items.
     */
    fun getAll(): Collection<MoneyTransferItem>

    /**
     * Retrieves all money transfer items received from a specific source account.
     *
     * @param source The source account.
     * @return A collection of money transfer items received from the source account.
     */
    fun getReceivedFromSource(source: Account): Collection<MoneyTransferItem>

    /**
     * Retrieves all money transfer items transferred to a specific target account.
     *
     * @param target The target account.
     * @return A collection of money transfer items transferred to the target account.
     */
    fun getTransferredToTarget(target: Account): Collection<MoneyTransferItem>

    /**
     * Retrieves all money transfer items for a specific account.
     *
     * @param account The account.
     * @return A collection of money transfer items for the account.
     */
    fun getForAccount(account: Account): Collection<MoneyTransferItem> {
        val receivedFromSource = getReceivedFromSource(account)
        val transferredToTarget = getTransferredToTarget(account)
        return receivedFromSource + transferredToTarget
    }
}