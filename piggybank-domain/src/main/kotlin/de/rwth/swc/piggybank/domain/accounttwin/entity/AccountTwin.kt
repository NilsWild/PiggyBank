package de.rwth.swc.piggybank.domain.accounttwin.entity

import de.rwth.swc.piggybank.domain.accounttwin.valueobject.AccountTwinId
import de.rwth.swc.piggybank.domain.shared.valueobject.AccountReference
import de.rwth.swc.piggybank.domain.shared.valueobject.Money

/**
 * Represents an account twin.
 */
data class AccountTwin(
    val id: AccountTwinId,
    val accountReference: AccountReference,
    val balance: Money
) {
    fun addToBalance(money: Money): AccountTwin {
        return copy(balance = this.balance + money)
    }

    fun subtractFromBalance(money: Money): AccountTwin {
        return copy(balance = this.balance - money)
    }
}