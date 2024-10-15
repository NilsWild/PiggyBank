package de.rwth.swc.piggybank.domain.accounttwin.spi

import de.rwth.swc.piggybank.domain.accounttwin.entity.AccountTwin
import de.rwth.swc.piggybank.domain.shared.valueobject.AccountReference

/**
 * Interface for managing Accounts
 */
interface Accounts {
    /**
     * Saves an account twin.
     */
    fun save(accountTwin: AccountTwin): AccountTwin

    /**
     * Retrieves an account twin for a given account reference.
     */
    fun findForAccount(accountReference: AccountReference): AccountTwin?
}