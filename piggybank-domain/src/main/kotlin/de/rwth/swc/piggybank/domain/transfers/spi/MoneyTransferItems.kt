package de.rwth.swc.piggybank.domain.transfers.spi

import de.rwth.swc.piggybank.domain.shared.valueobject.Account
import de.rwth.swc.piggybank.domain.transfers.entity.MoneyTransferItem

/**
 * Interface for managing money transfer items.
 */
interface MoneyTransferItems {

    /**
     * Saves a money transfer item.
     *
     * @param item The money transfer item to save.
     */
    fun save(item: MoneyTransferItem)

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
    fun getAllReceivedFromSource(source: Account): Collection<MoneyTransferItem>

    /**
     * Retrieves all money transfer items transferred to a specific target account.
     *
     * @param target The target account.
     * @return A collection of money transfer items transferred to the target account.
     */
    fun getAllTransferredToTarget(target: Account): Collection<MoneyTransferItem>
}