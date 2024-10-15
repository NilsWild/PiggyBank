package de.rwth.swc.piggybank.domain.transferclassification.api.dto

import java.util.*

/**
 * Reference to a transfer.
 *
 * @property value The value of the reference.
 */
@JvmInline
value class TransferReference(val value: UUID) {
    override fun toString(): String {
        return value.toString()
    }
}