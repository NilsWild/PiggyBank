package de.rwth.swc.piggybank.domain.accounttwin.api

import de.rwth.swc.piggybank.domain.accounttwin.api.dto.AccountTwinCreationRequest
import de.rwth.swc.piggybank.domain.accounttwin.api.dto.Transfer
import de.rwth.swc.piggybank.domain.accounttwin.entity.AccountTwin
import de.rwth.swc.piggybank.domain.shared.valueobject.AccountReference

/**
 * Interface for handling events regarding AccountTwins
 */
interface AccountTwinService {

    /**
     * Creates an AccountTwin for a given account reference.
     *
     * @param accountReference The account reference for which the AccountTwin should be created.
     */
    fun createAccountTwin(request: AccountTwinCreationRequest): AccountTwin

    /**
     * Handles a transfer of money.
     *
     * @param transfer The transfer to be handled.
     */
    fun handleTransfer(transfer: Transfer)
}