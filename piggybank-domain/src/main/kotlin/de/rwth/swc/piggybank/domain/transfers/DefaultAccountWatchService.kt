package de.rwth.swc.piggybank.domain.transfers

import de.rwth.swc.piggybank.domain.shared.valueobject.AccountReference
import de.rwth.swc.piggybank.domain.transfers.api.AccountWatchService
import de.rwth.swc.piggybank.domain.transfers.entity.AccountWatch
import de.rwth.swc.piggybank.domain.transfers.spi.AccountWatches
import de.rwth.swc.piggybank.domain.transfers.valueobject.AccountWatchId
import java.util.*

/**
 * Default implementation of the [AccountWatchService].
 */
class DefaultAccountWatchService(private val watches: AccountWatches) : AccountWatchService {
    override fun addWatchedAccount(accountReference: AccountReference): AccountWatch {
        val existingWatch = watches.findForAccount(accountReference)
        if(existingWatch != null) {
            return existingWatch
        }
        val watch = AccountWatch(AccountWatchId(UUID.randomUUID()), accountReference)
        return watches.save(watch)
    }

    override fun isAccountWatched(accountReference: AccountReference): Boolean {
        return watches.findForAccount(accountReference) != null
    }

}