package de.rwth.swc.piggybank.domain.accounttwin

import de.rwth.swc.piggybank.domain.accounttwin.api.AccountTwinService
import de.rwth.swc.piggybank.domain.accounttwin.api.dto.AccountTwinCreationRequest
import de.rwth.swc.piggybank.domain.accounttwin.api.dto.Transfer
import de.rwth.swc.piggybank.domain.accounttwin.entity.AccountTwin
import de.rwth.swc.piggybank.domain.accounttwin.spi.Accounts
import de.rwth.swc.piggybank.domain.shared.valueobject.Money
import de.rwth.swc.piggybank.domain.shared.valueobject.MoneyAmount

/**
 * Manages AccountTwins.
 */
class AccountTwinManager(private val accounts: Accounts): AccountTwinService {
    override fun createAccountTwin(request: AccountTwinCreationRequest): AccountTwin {
        val newAccountTwin = AccountTwin(request.id, request.accountReference, Money(MoneyAmount(0),request.currency))
        return accounts.save(newAccountTwin)
    }

    override fun handleTransfer(transfer: Transfer) {
        val sourceAccount = accounts.findForAccount(transfer.source)
        val targetAccount = accounts.findForAccount(transfer.target)

        if (sourceAccount != null) {
            val sourceAccountWithNewBalance = sourceAccount.subtractFromBalance(transfer.amount)
            accounts.save(sourceAccountWithNewBalance)
        }

        if(targetAccount != null) {
            val targetAccountWithNewBalance = targetAccount.addToBalance(transfer.amount)
            accounts.save(targetAccountWithNewBalance)
        }
    }

}