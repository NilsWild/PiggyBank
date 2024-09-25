package de.rwth.swc.piggybank.domain.accounttwin.spi

import de.rwth.swc.piggybank.domain.accounttwin.entity.AccountTwin
import de.rwth.swc.piggybank.domain.shared.valueobject.Account

interface AccountTwins {
    /**
     * Saves a money transfer item, overriding existing AccountTwins with the same account fields
     *
     * @param account The account twin item to save.
     */
    fun save(account: AccountTwin)

    /**
     * Retrieves all account twins.
     *
     * @return A collection of all account twins.
     */
    fun getAll(): Collection<AccountTwin>

    /**
     * Retrieves the account twin for the account.
     *
     * @param account The account to retrieve the account twin for.
     * @return The account twin for the account if it exists, else null.
     */
    fun getAccount(account: Account): AccountTwin
}