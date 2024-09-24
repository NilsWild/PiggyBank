package de.rwth.swc.piggybank.domain.transfers.valueobject

import java.util.*

/**
 * Inline value class for the UUID.
 *
 * @property value The UUID value.
 */
@JvmInline
value class MoneyTransferItemId(val value: UUID) {
    override fun toString(): String {
        return value.toString()
    }
}