package de.rwth.swc.piggybank.domain.transfers.api

import de.rwth.swc.piggybank.domain.shared.valueobject.AccountReference
import de.rwth.swc.piggybank.domain.transfers.entity.AccountWatch

/**
 * Service Interface for managing accounts to watch.
 */
interface AccountWatchService {
    /**
     * Adds an account to the list of watched accounts.
     *
     * @param accountReference The account to watch.
     */
    fun addWatchedAccount(accountReference: AccountReference): AccountWatch

    /**
     * Checks if an account is watched.
     *
     * @param accountReference The account to check.
     */
    fun isAccountWatched(accountReference: AccountReference): Boolean
}