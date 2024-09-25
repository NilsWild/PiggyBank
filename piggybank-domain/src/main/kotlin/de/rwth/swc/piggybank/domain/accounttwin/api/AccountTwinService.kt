package de.rwth.swc.piggybank.domain.accounttwin.api

import de.rwth.swc.piggybank.domain.shared.valueobject.Account
import de.rwth.swc.piggybank.domain.shared.valueobject.Money

interface AccountTwinService {
    /**
     * Applies a transaction in the account twins
     */
    fun registerTransaction(source: Account, target: Account, amount: Money)
}