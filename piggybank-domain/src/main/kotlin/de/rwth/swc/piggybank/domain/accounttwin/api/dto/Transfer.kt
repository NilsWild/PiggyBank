package de.rwth.swc.piggybank.domain.accounttwin.api.dto

import de.rwth.swc.piggybank.domain.shared.valueobject.AccountReference
import de.rwth.swc.piggybank.domain.shared.valueobject.Money
import de.rwth.swc.piggybank.domain.shared.valueobject.TransferReference

/**
 * Data class representing a transfer.
 */
data class Transfer (
    val id: TransferReference,
    val amount: Money,
    val source: AccountReference,
    val target: AccountReference
)