package de.rwth.swc.piggybank.domain.transferclassification.api.dto

import de.rwth.swc.piggybank.domain.shared.valueobject.Money

/**
 * Data class representing a transfer.
 */
data class Transfer (
    val id: TransferReference,
    val amount: Money,
    val valueDate: ValueDate,
    val purpose: Purpose
)

