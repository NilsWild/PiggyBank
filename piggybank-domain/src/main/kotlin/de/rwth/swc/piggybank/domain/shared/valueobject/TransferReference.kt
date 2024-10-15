package de.rwth.swc.piggybank.domain.shared.valueobject

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