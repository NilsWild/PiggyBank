package de.rwth.swc.piggybank.domain.transfers.spi

import de.rwth.swc.piggybank.domain.shared.valueobject.AccountReference
import de.rwth.swc.piggybank.domain.transfers.entity.AccountWatch

/**
 * Interface for managing account watches.
 */
interface AccountWatches {
    /**
     * Saves an account watch.
     *
     * @param accountWatch The account watch to save.
     * @return The saved account watch.
     */
    fun save(accountWatch: AccountWatch): AccountWatch

    /**
     * Finds an account watch by its referenced account
     *
     * @param accountReference The account reference to search for.
     * @return The found account watch, or null if not found.
     */
    fun findForAccount(accountReference: AccountReference): AccountWatch?
}