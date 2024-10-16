package de.rwth.swc.piggybank.domain.transfers.entity

import de.rwth.swc.piggybank.domain.shared.valueobject.AccountReference
import de.rwth.swc.piggybank.domain.transfers.valueobject.AccountWatchId

/**
 * Represents an account watch. only those accounts that have an account watch are further processed.
 */
data class AccountWatch(
    val id: AccountWatchId,
    val accountReference: AccountReference
)